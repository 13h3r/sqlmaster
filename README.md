# Welcome! What is SQL Master? 

SQLMaster is sql template engine that provides SQL valid templates. This allows you to have only one SQL template both for SQL development in SQL editor and in you program code to dynamically generate SQL code

Here it is:

```sql
select * from client where name = /**string name(*/'John'/**)*/
```

You can copy-paste it to your favorite sql editor. Also this template contains markup information and you can generate sql by specifying parameter values

```java
Template t = Engine.load("select * from client where name = /**string name(*/'John'/**)*/");
t.assignValue("name", "Kate");
```

and the result is

```sql
select * from client where name = 'Kate'
```

# Syntax
Main goal of SQLMaster is to have only one template - both for SQL development and template engine. 
Template is plain SQL and markup placed inside comments.

# Parameters
Parameters is markup element for parameter values substitutions. Here it is typical usage

```sql
select * from client where name = /**string client(*/'John'/**)*/
```

Each parameter have name and type. In example above name is "client" and type is "string". Name used to identify parameter. Type is SQL type used to control type values at runtime.

# Embedded text

# Java API

# Type parameters 
