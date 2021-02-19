package github.etx.neo4j

import org.neo4j.driver.Record

class CursorWrapper(private val record: Record, private val sr: List<Record>) : Sequence<CursorWrapper> {

    private class CursorIterator(val sr: List<Record>) : Iterator<CursorWrapper> {
        override fun next(): CursorWrapper {
            return CursorWrapper(sr.iterator().next(), sr)
        }

        override fun hasNext(): Boolean {
            return sr.iterator().hasNext()
        }
    }

    override fun iterator(): Iterator<CursorWrapper> {
        return CursorIterator(sr)
    }

    fun unwrap(key: String): Cursor {
        return Cursor(record[key])
    }

}
