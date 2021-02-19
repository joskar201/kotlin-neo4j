package github.etx.neo4j

import org.neo4j.driver.Driver
import org.neo4j.driver.internal.InternalRecord


class NeoQuery(private val driver: Driver, private val serializer: INeoSerializer) {

    fun submit(query: String, parameters: Map<String, Any?> = mapOf()): CursorWrapper {
        val session = driver.session()

        return session.use {
            val _parameters = serializer.serialize(parameters)
            val result = session.run(query, _parameters)
            if (result.hasNext()) {
                CursorWrapper(result.peek(), result.list())
            } else {
                CursorWrapper(InternalRecord(listOf(), arrayOf()), result.list())
            }
        }
    }

}