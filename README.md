# Query
A Java library that provides a fluent api to write queries. (Dead)


### Syntax

#### Simple Queries

```java
Query.with("A").and().param("B"); // "A" AND "B"

Query.with("A").or().param("B"); // "A" OR "B"

Query.with("A").and().group(Group.with("B").and().param("C")); // "A" AND ("B" AND "C")

Query.with("A").and().beginGroup().param("B").and().param("C").endGroup(); // "A" AND ("B" AND "C")
```


#### Smart

```java
Query.with("A").and().or().param("B"); // "A" OR "B"
Query.with("A").or().and().param("B"); // "A" AND "B"
Query.with("A").and().or().and().or().param("B"); // "A" OR "B"
Query.with("A").or().and().or().and().param("B"); // "A" AND "B"

Query.with("A").and().param("B").param("C"); // "A" AND "C"

Query.with("A").and().beginGroup().param("B"); // "A" AND "B"

Query.with("A").and().beginGroup().param("B").and().param("C"); // "A" AND ("B" AND "C")
```


#### Empty Query

```java
Query.empty();
```


#### toString

```java
System.out.println(query.getAsString());
```


#### Convert to and from JSON

```java
String json = query.toJson(true);

System.out.println(json);

Query query = Query.fromJson(json);
```


#### Complex Queries

```java

// "A" AND ("B" OR "C") OR ("C" AND ("D" OR "E"))
Query query = Query.with("A")
    .and()
    .beginGroup()
    .param("B")
    .or()
    .param("C")
    .endGroup()
    .or()
    .beginGroup()
    .param("C")
    .and()
    .group(Group.with("D").or().param("E"))
    .endGroup();
    

// "A" AND "B" OR ("C" AND "D") AND ("C" AND ("D" OR "E"))
Query query1 = Query.with("A")
    .and()
    .param("B")
    .or()
    .beginGroup()
    .param("C")
    .and()
    .param("D")
    .endGroup()
    .and()
    .beginGroup()
    .param("C")
    .and()
    .group(Group.with("D").or().param("E"))
    .endGroup();

// "A" AND ("B" OR "C") OR ("D" AND ("E" OR "F" AND ("G" AND "H") AND ("I" OR "J") OR ("K" AND "L") AND ("M" OR "N") AND "O") AND "P")
Query query3 = Query.with("A")
    .and()
    .beginGroup()
    .param("B")
    .or()
    .param("C")
    .endGroup()
    .or()
    .beginGroup()
    .param("D")
    .and()
    .beginGroup()
    .param("E")
    .or()
    .param("F")
    .and()
    .beginGroup()
    .param("G")
    .and()
    .param("H")
    .endGroup()
    .and()
    .group(Group.with("I").or().param("J"))
    .or()
    .beginGroup()
    .param("K")
    .and()
    .param("L")
    .endGroup()
    .and()
    .group(Group.with("M").or().param("N"))
    .and()
    .param("O")
    .endGroup()
    .and()
    .param("P")
    .endGroup();

```

