/*
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.jmx.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.management.MBeanServer;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.helpers.Service;
import org.neo4j.kernel.impl.transaction.state.DataSourceManager;
import org.neo4j.kernel.internal.KernelData;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

public class JmxKernelExtension extends LifecycleAdapter
{
    private final KernelData kernelData;
    private final DataSourceManager dataSourceManager;
    private final Log log;
    private List<Neo4jMBean> beans;
    private MBeanServer mbs;
    private ManagementSupport support;

    public JmxKernelExtension( KernelData kernelData, DataSourceManager dataSourceManager, LogProvider logProvider )
    {
        this.kernelData = kernelData;
        this.dataSourceManager = dataSourceManager;
        this.log = logProvider.getLog( getClass() );
    }

    @Override
    public void start()
    {
        support = ManagementSupport.load();
        mbs = support.getMBeanServer();
        beans = new LinkedList<>();
        try
        {
            Neo4jMBean bean = new KernelBean( kernelData, 	dataSourceManager, support );
            mbs.registerMBean( bean, bean.objectName );
            beans.add( bean );
        }
        catch ( Exception e )
        {
            log.info( "Failed to register Kernel JMX Bean" );
        }

        for ( ManagementBeanProvider provider : Service.load( ManagementBeanProvider.class ) )
        {
            try
            {
                for ( Neo4jMBean bean : provider.loadBeans( kernelData, support ) )
                {
                    mbs.registerMBean( bean, bean.objectName );
                    beans.add( bean );
                }
            }
            catch ( Exception e )
            {
                log.info( "Failed to register JMX Bean " + provider + " (" + e + ")" );
            }
        }
        try
        {
            Neo4jMBean bean = new ConfigurationBean( kernelData, support );
            mbs.registerMBean( bean, bean.objectName );
            beans.add( bean );
        }
        catch ( Exception e )
        {
            log.info( "Failed to register Configuration JMX Bean" );
        }
    }

    @Override
    public void stop()
    {
        for ( Neo4jMBean bean : beans )
        {
            try
            {
                mbs.unregisterMBean( bean.objectName );
            }
            catch ( Exception e )
            {
                log.warn( "Could not unregister MBean " + bean.objectName.toString(), e );
            }
        }
    }

    public final <T> T getSingleManagementBean( Class<T> type )
    {
        Iterator<T> beans = getManagementBeans( type ).iterator();
        if ( beans.hasNext() )
        {
            T bean = beans.next();
            if ( beans.hasNext() )
            {
                throw new NotFoundException( "More than one management bean for " + type.getName() );
            }
            return bean;
        }
        throw new NotFoundException( "No management bean found for " + type.getName() );
    }

    private <T> Collection<T> getManagementBeans( Class<T> beanInterface )
    {
        Collection<T> result = null;
        if ( support.getClass() != ManagementSupport.class && beans.size() > 0 && beans.get( 0 ) instanceof KernelBean )
        {
            try
            {
                result = support.getProxiesFor( beanInterface, (KernelBean) beans.get( 0 ) );
            }
            catch ( UnsupportedOperationException ignore )
            {
                // go to fall back
            }
        }
        if ( result == null )
        {
            // Fall back: if we cannot create proxy, we can search for instances
            result = new ArrayList<>();
            for ( Neo4jMBean bean : beans )
            {
                if ( beanInterface.isInstance( bean ) )
                {
                    result.add( beanInterface.cast( bean ) );
                }
            }
        }
        return result;
    }
}
