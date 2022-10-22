package com.springbucks.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.alibaba.druid.wall.spi.MySqlWallProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

public class DruidCheck {


    public  Boolean checkSql(String sql){
        Boolean flag=false;
        if(sqlValidate(sql)) {
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
            MySqlSchemaStatVisitor visitor1 = null;
            for (int j = 0; j < stmtList.size(); j++) {
                SQLStatement stmt = stmtList.get(j);
                visitor1 = new MySqlSchemaStatVisitor();
                stmt.accept(visitor1);
            }
                for (int i = 0; i < visitor1.getConditions().size(); i++) {
                    if (visitor1.getConditions().get(i).toString().toUpperCase().contains("IN")) {
                        String sqlArr[] = visitor1.getConditions().get(i).toString().split(",");
                        if (sqlArr.length > 10) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }
                    }
                }
        }
        return flag;
    }
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String [] args){
        DruidCheck druidCheck=new DruidCheck();
        String name="aa";
        System.out.println(druidCheck.checkSql("select ename+':'+sal from tb_1 where id in('aa','bb','cc','dd','ee','ff','gg','hh') and name= '" + name+ "' and sex='femal'"));
        /*MySqlStatementParser parser = new MySqlStatementParser("select * from tb_1 where id in('aa','bb','cc','dd','ee','ff','gg','hh') and name='test' and sex='femal'");
        List<SQLStatement> stmtList = SQLUtils.parseStatements("select name,id,sex,addr from tb_1 where id in('aa','bb','cc','dd','ee','ff','gg','hh') and name='test' and sex='femal'",JdbcConstants.MYSQL);
        MySqlSchemaStatVisitor visitor1=null;
        for (int j=0;j<stmtList.size();j++){
            SQLStatement stmt=stmtList.get(j);
            visitor1=new MySqlSchemaStatVisitor();
            stmt.accept(visitor1);
        }
        System.out.println(visitor1.getColumns());*/

    }
}
