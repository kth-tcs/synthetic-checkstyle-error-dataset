/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.dict.global;

import com.google.common.base.Preconditions;
import org.apache.kylin.dict.BytesConverter;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Encapsulates the metadata for a particular version of the global dictionary.
 * Usually each version of a global dictionary stores its metadata in an index file.
 */
public class GlobalDictMetadata {
    public final int baseId;
    public final int maxId;
    public final int maxValueLength;
    public final int nValues;
    public final BytesConverter bytesConverter;
    public final TreeMap<AppendDictSliceKey, String> sliceFileMap; // slice key -> slice file name

    public GlobalDictMetadata(int baseId, int maxId, int maxValueLength, int nValues, BytesConverter bytesConverter, NavigableMap<AppendDictSliceKey, String> sliceFileMap) {

        Preconditions.checkNotNull(sliceFileMap, "sliceFileMap");

        this.baseId = baseId;
        this.maxId = maxId;
        this.maxValueLength = maxValueLength;
        this.nValues = nValues;        this.bytesConverter = bytesConverter;
        this.sliceFileMap = new TreeMap<>(sliceFileMap);
    }
}
