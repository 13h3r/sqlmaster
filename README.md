# Welcome! What is SQL Master? 

SQLMaster is sql template engine that works SQL valid templates. This allows you to have only one SQL template both for SQL development in SQL editor and in you program code to dynamically generate SQL code

Here it is:

```sql
select * from client where name = /** string name(*/'John'/**)*/
```

You can copy-paste it to your favorite sql editor. Also this template contains markup information and you can generate sql by specifying parameter values

```java
Template t = Engine.load("select * from client where name = /** string name(*/'John'/**)*/");
t.assignValue("name", "Kate");
t.process();
```

and the result is

```sql
select * from client where name = 'Kate'
```

# Concepts
## Syntax
Main goal of SQLMaster is to have only one template - both for SQL development and template engine. 
Template is plain SQL and markup placed inside javadoc comments - `/**` and `*/`

There are two kind of syntax constructs: parameters and embedded text.

## Template
To load template use `Engine`:

```java
String templateText = ...;
Template t = Engine.load(templateText);
```

`Template` provides API to work with template.

To generate template simple call `process` method of `Template`

## Parameters
Parameters is markup element for parameter values substitutions. Here it is typical usage

```sql
select * from client where name = /** string client(*/'John'/**)*/
```

Each parameter have name and type. In example above name is "client" and type is "string". Name is used to identify parameter in template. Type is SQL type used to control type values at runtime.

### Default values
Parameter may contains plaintext inside it to provide some kind of 'default values'. These values used to simplify work in SQL editor and will be removed in time of template procesing. These two templates are equivalent:

```sql
select * from client where name = /** string client(*/'John'/**)*/
```


```sql
select * from client where name = /** string client()*/
```

To assign parameter value use `assignValue` method of `Template`:

```java
template.assignValue("client", "Mike")
```

## Embedded text

# Java API
## Template loading
## Type parameters

# Best practices

