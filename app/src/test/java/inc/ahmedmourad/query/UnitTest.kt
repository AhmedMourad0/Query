package inc.ahmedmourad.query

import org.junit.Test

import inc.ahmedmourad.query.elements.Group

import org.junit.Assert.assertEquals

class UnitTest {

    @Test
    fun query_isValid() {

        val query = Query.with("A")
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
                .endGroup()
                .asString

        val query1 = Query.with("A")
                .and()
                .param("B")
                .and()
                .param("B")
                .or()
                .beginGroup()
                .param("B")
                .or()
                .param("B")
                .endGroup()
                .or()
                .and()
                .beginGroup()
                .param("C")
                .and()
                .group(Group.with("D").or().param("E"))
                .endGroup()
                .asString

        val query2 = Query.with("A")
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
                .group(Group.with("E").or().param("F"))
                .endGroup()
                .asString

        val query3 = Query.with("A")
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
                .endGroup()

        println(query)
        println(query1)
        println(query2)
        System.out.println(query3.asString)

        val json = query3.toJson(true)

        println(json)

        val query4 = Query.fromJson(json).asString

        println(query4)

        System.out.println(Query.empty().toJson())

        assertEquals("\"A\" AND (\"B\" OR \"C\") OR (\"D\" AND (\"E\" OR \"F\" AND (\"G\" AND \"H\") AND (\"I\" OR \"J\") OR (\"K\" AND \"L\") AND (\"M\" OR \"N\") AND \"O\") AND \"P\")", query3.getAsString())
        assertEquals("\"A\" AND (\"B\" OR \"C\") OR (\"D\" AND (\"E\" OR \"F\" AND (\"G\" AND \"H\") AND (\"I\" OR \"J\") OR (\"K\" AND \"L\") AND (\"M\" OR \"N\") AND \"O\") AND \"P\")", query4)
    }
}
