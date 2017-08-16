mybatis-time是mybatis的一个扩展插件，主要是为了解决自动插入createTime与updateTime字段

## 起由

 jedi项目内部规范为，createTime与updateTime为not null。
 
## 扩展

1、 使用了plugins,增加拦截器，在执行SQL之前进行拦截，先判断SQL中存不存在createTime与updateTime，如果不存在就插入该字段

2.  该插件只拦截insert与update 



## 如何使用
 
#### 以**Gradle**为例
 
1、 引用插件到项目
 
 ```$xslt

compile(group: 'com.huifenqi.jedi', name: 'mybatis-time', version: '1.0-SNAPSHOT')
```


 3、在MasterDataSourceConfig连接数据库增加以下配置即可
 
 ```$xslt
  sqlSessionFactory方法中，在return 之前加入：
 
         //plugins插件，添加updateTime修改时间字段
 
         sessionFactory.setPlugins(new Interceptor[]{
                 new CommonDataInterceptor("","updateTime")
         });
         
    CommonDataInterceptor 类一共有两个参数：
    1.createTime 创建时间  
    2.updateTime 修改时间
    根据实际情况判断传入不传
```
 
 