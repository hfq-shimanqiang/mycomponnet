package com.huifenqi.jedi;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lib on 2017/8/10.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class CommonDataInterceptor implements Interceptor {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("-yyyy-MM-dd HH:mm:ss.SSS-");

    /*修改时间*/
    private String updateTime;
    /*创建时间*/
    private String createTime;

    public CommonDataInterceptor(String createTime, String updateTime) {
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(handler);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");

        Object parameterObject = boundSql.getParameterObject();
        SqlCommandType sqlCmdType = ms.getSqlCommandType();
        //获取原始sql
        String originalSql = boundSql.getSql();
        //追加SQL参数
        String newSql = "";
        if (sqlCmdType == SqlCommandType.INSERT) {
            if (null != createTime && !createTime.isEmpty()) {
                newSql = addInsertData(parameterObject, originalSql, "f_create_time", createTime);
                //修改原始sql
                if (newSql.length() > 0) {
                    metaObject.setValue("delegate.boundSql.sql", newSql);
                }
            }
            if (null != updateTime && !updateTime.isEmpty()) {
                originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
                newSql = addInsertData(parameterObject, originalSql, "f_update_time", updateTime);
                //修改原始sql
                if (newSql.length() > 0) {
                    metaObject.setValue("delegate.boundSql.sql", newSql);
                }
            }
        } else if (sqlCmdType == SqlCommandType.UPDATE) {
            if (null != updateTime && !updateTime.isEmpty()) {
                newSql = addInsertData(parameterObject, originalSql, "f_update_time", updateTime);
                //修改原始sql
                if (newSql.length() > 0) {
                    metaObject.setValue("delegate.boundSql.sql", newSql);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 如果不存在，就添加参数字段
     *
     * @param parameterObject
     * @param originalSql
     * @param fieldName
     * @param timeFileName
     * @return
     */
    private String addInsertData(Object parameterObject, String originalSql, String fieldName, String timeFileName) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(originalSql);
            if (stmt instanceof Insert) {
                //如果是添加
                Insert update = (Insert) stmt;
                List<Column> columns = update.getColumns();
                if (!contains(columns, fieldName)) {
                    Column versionColumn = new Column();
                    versionColumn.setColumnName(fieldName);
                    columns.add(versionColumn);
                    ItemsList itemList = update.getItemsList();
                    ExpressionList expressionList = (ExpressionList) itemList;
                    List<Expression> expressions = expressionList.getExpressions();
                    expressions.add(new TimestampValue(TIMESTAMP_FORMAT.format(new Date())));
                } else {
                    getFieldValue(parameterObject, timeFileName);
                }
            } else if (stmt instanceof Update) {
                //如果是添加
                Update update = (Update) stmt;
                List<Column> columns = update.getColumns();
                if (!contains(columns, fieldName)) {
                    Column versionColumn = new Column();
                    versionColumn.setColumnName(fieldName);
                    columns.add(versionColumn);
                    List<Expression> expressions = update.getExpressions();
                    expressions.add(new TimestampValue(TIMESTAMP_FORMAT.format(new Date())));
                } else {
                    getFieldValue(parameterObject, timeFileName);
                }
            }
            return stmt.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return originalSql;
        }
    }

    private boolean contains(List<Column> columns, String fieldName) {
        if (columns == null || columns.size() <= 0) {
            return false;
        }
        if (fieldName == null || fieldName.length() <= 0) {
            return false;
        }
        for (Column column : columns) {
            if (column.getColumnName().indexOf(fieldName) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给传入为空的参数赋值
     *
     * @param parameterObject
     * @param fieldName
     */
    private void getFieldValue(Object parameterObject, String fieldName) {
        Object value = null;
        MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameterObject;
        Object param1 = map.get("param1");
        if (null != param1) {
            MetaObject metaObject = SystemMetaObject.forObject(param1);
            if (null != metaObject) {
                value = metaObject.getValue(fieldName);
                if (value == null) {
                    metaObject.setValue(fieldName, new Date());
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
