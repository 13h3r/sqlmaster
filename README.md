# What is SQL Master

SQLMaster is sql template engine that provides SQL valud templates. This allows you to have only one SQL template both for SQL development in SQL editor and in you programm code to dynamically generate SQL code

Here it is:

```sql
select * from client where name = /**string name(*/'John'/**)*/
```

You can copy-paste it to your favorite sql editor. Also this template contains markup information and you can generate sql by spicifying parameter values

```java
Template t = Engine.load("select * from client where name = /**string name(*/'John'/**)*/");
t.assignValue("name", "Kate");
```

and the result is

```sql
select * from client where name = 'Kate'
```


# Parameters

# Section

# Java API
