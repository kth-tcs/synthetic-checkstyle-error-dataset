/* Generated By:JJTree: Do not edit this line. AstParenthesis.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstParenthesis extends SimpleNode
{
    public AstParenthesis(int id)
    {
        super(id);
        this.nodePrefix = "(" ;
        this.nodeSuffix = ")";
    }

    public AstParenthesis()
    {
        this(SqlParserTreeConstants.JJTPARENTHESIS);
    }

    public AstParenthesis(SimpleNode child)
    {
        this(SqlParserTreeConstants.JJTPARENTHESIS);
        addChild(child);
    }
}
/* JavaCC - OriginalChecksum=1df5ba850c3248269997dcf63546835b (do not edit this line) */
