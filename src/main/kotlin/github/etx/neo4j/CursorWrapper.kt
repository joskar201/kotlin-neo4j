package github.etx.neo4j

import org.neo4j.driver.Record
import org.neo4j.driver.Result

class CursorWrapper(private val record: Record, private val sr: Result) : Sequence<CursorWrapper> {

    private class CursorIterator(val sr: Result) : Iterator<CursorWrapper> {
        override fun next(): CursorWrapper {
            return CursorWrapper(sr.next(), sr)
        }

        override fun hasNext(): Boolean {
            return sr.hasNext()
        }
    }

    override fun iterator(): Iterator<CursorWrapper> {
        return CursorIterator(sr)
    }

    fun unwrap(key: String): Cursor {
        return Cursor(record[key])
    }

}
