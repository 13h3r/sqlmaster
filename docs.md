
# Concepts

## Syntax
Main goal of SQLMaster is to have only one template - both for SQL development and template engine. 
Template is plain SQL and markup placed inside javadoc comments - `/**` and `*/`

There are two kind of syntax constructs: parameters and embedded text.

## Template
Typical workflow for SQL Master:
- load template
- assign parameter values
- enable embedded text
- render template

To load template use `Engine`:

```java
String templateText = ...;
Template t = Engine.load(templateText);
```

`Template` provides API to work with template.

To generate template simple call `process` method of `Template`

## Parameters
Parameter is a markup element for parameter values substitution. Here it is typical usage:

```sql
select * from client where name = /** string client(*/'John'/**)*/
```

Each parameter have name and type. In example above name is "client" and type is "string". Name is used to identify parameter in template. Type is SQL type used to control type values at runtime.

To assign parameter value use `assignValue` method of `Template`:

```java
template.assignValue("client", "Mike")
```

You can place multiple parameters with same name in template. When you assign value for such parameters all occurences will be rendered with given value.

If parameter should be rendered and no value assigned to it exception occurs.

Parameters can contains only plaintext inside of them.

### Default values
Parameter may contains plaintext inside it to provide 'default values'. These values used to simplify work in SQL editor and will be removed in time of template rendering. These two templates are equivalent:

```sql
select * from client where name = /** string client(*/'John'/**)*/
```

```sql
select * from client where name = /** string client()*/
```

## Embedded text
Embedded text is used to insert some predefined text in you template. Most time it used to add some optional joins or conditions. Here it is syntax example:

```sql
selet * 
from client c 
/** fullInfo{*/inner join client_info ci on ci.client_id = c.id /**}*/
```

By default embedded text is not rendered. There are two ways to render embedded text:
- explicit call of `enable` method
- cascade enable (read bellow)

Embedded text can contains another embedded text and parameters.

## Cascade enable
Most case when you need embedded text is options where clause or optional joins. Both of them should be enabled only when you set value of parameter. Here it is typical usage:

```sql
select * from client c
/** additionalInfo{*/ inner join client_info ci on ci.client_id = c.id /**}*/
where c.name = /** string name(*/'john'/**)*/
/** additionalInfo{*/ and ci.address = /** string address(*/'Novosibirsk'/**)}*/
```
Here you can see two parameters - `name` and `address`. `address` is placed inside embedded text

## Anonymous embedded text

## Tree

## Cascade activation

# Java API
## Template loading
## Type parameters

# Best practices
