# Welcome! What is SQL Master? 

SQLMaster is sql template engine that works SQL valid templates. This allows you to have only one SQL template both for SQL development in SQL editor and in you program code to dynamically generate SQL code

# Ten minutes tutorial

## Step 0 - Adding SQLMaster to project
Add maven repository

```xml
<repository>
    <id>sonatype-nexus-snapshots</id>
    <name>Sonatype Nexus Snapshots</name>
    <url>http://oss.sonatype.org/content/groups/public</url>
</repository>
```

and dependency

```xml
<dependency>
    <groupId>com.github.13h3r.sqlmaster</groupId>
    <artifactId>core</artifactId>
    <version>1.0</version>
</dependency>
```

## Step 1 - Parameters

For this tutorial assume you are searching for client by name:

```sql
select * from client where name = 'John'
```

Here you have parameter `name`. Markup with SQLMaster:

```sql
select * from client where name = /**string clientName(*/'John'/**)*/
```

You still have parameter value and you template is still sql valid and can be placed on your SQL editor as usual. Markup placed betwen `/**` and `*/`. `string` is a type of parameter and `clientName` is parameter name. Parameter value will be put inside `(` `)` symbols of markup. All text inside `(` `)` will be removed at render time.

Here it is how to use template in java:

```java
Template t = SimpleEngine.create(
        "select * from client where name = /**string clientName(*/'John'/**)*/");
t.assignValue("clientName", "Mary");
System.out.println(t.process());
```

and the result is

```sql
select * from client where name = 'Mary'
```

## Step 2 - Embedded text
To manage sql you need not only fill parameters placeholders, but enable/disable some parts of sql like joins and conditions. You can do it with embedded text in SQL Master
Assume your clients have orders and you can search client by name (required) or order number (optional):

```sql
select * from client c
inner join order o on o.client_id = c.id
where c.name = 'John'
and o.num = 123456
```

First - lets markup parameters `clientName` and `orderNum`:

```sql
select * from client c
inner join order o on o.client_id = c.id
where c.name = /**string clientName(*/'John'/**)*/
and o.num = /**number orderNum(*/123456/**)*/
```

But what should we do if `orderNum` is not set. We need just do not render some conditional text related to `orderNum`:

```sql
select * from client c
inner join order o on o.client_id = c.id
where c.name = /**string clientName(*/'John'/**)*/
/**order{*/and o.num = /**number orderNum(*/123456/**)}*/
```

You now faced with embedded text markup. Embedded text placed between `{` `}` symbols. Embedded text may have or may have not name. In this example embedded text have name `order`. Embedded text are not rendered by default. To render it you should assignValue to some parameter inside it or just call `enable` method of `Template`.

Some java code:

```java
Template t = SimpleEngine.create(templateAbove);
t.assignValue("clientName", "Mary");
System.out.println(t.process());
t.assignValue("orderNum", 98);
System.out.println(t.process());
```

and result

```sql
select * from client c
inner join order o on o.client_id = c.id
where c.name = 'Mary'

select * from client c
inner join order o on o.client_id = c.id
where c.name = 'Mary'
and o.num = 98
```

## Step 3 - Embedded text (advanced part)
What is wrong with previous example? You do not need this `join` on orders when `orderNum` parameter is not set. The solution is to use embedded text with the same name again:

Template:

```sql
select * from client c
/**order{*/inner join order o on o.client_id = c./**}*/
where c.name = /**string clientName(*/'John'/**)*/
/**order{*/and o.num = /**number orderNum(*/123456/**)}*/
```

Java:

```java
Template t = SimpleEngine.create(templateAbove);
t.assignValue("clientName", "Mary");
System.out.println(t.process());
t.assignValue("orderNum", 98);
System.out.println(t.process());
```

and result

```sql
select * from client c
where c.name = 'Mary'

select * from client c
inner join order o on o.client_id = c.id
where c.name = 'Mary'
and o.num = 98
```