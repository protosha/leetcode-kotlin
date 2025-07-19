package com.github.protosha.lrucache

object LruCache {
    @JvmStatic
    fun main(args: Array<String>) {
        val ops: List<(LRUCache) -> String> = listOf(
            put(1, 1),
            get(1),
            put(2, 2),
            put(3, 3),
            get(1),
            put(2, 22),
        )
        val cache = LRUCache(2)
        println("Initial: $cache")
        for (op in ops) {
            println(cache.execute(op))
            println("$cache")
        }
    }

    private fun put(key: Int, value: Int): (LRUCache) -> String = { it.put(key, value).let { "put(${key}, ${value}): OK" } }
    private fun get(key: Int): (LRUCache) -> String = { it.get(key).let { value -> "get(${key}): $value" } }

    private fun LRUCache.execute(block: (LRUCache) -> String) = block(this)
}

class LRUCache(val capacity: Int) {
    private val map = LinkedHashMap<Int, CacheNode>(capacity + 1)
    private val list = DoubleLinkedList()

    fun get(key: Int): Int {
        return map[key]?.let {
            list.pushHead(it)
            it.value
        } ?: -1
    }

    fun put(key: Int, value: Int) {
        if (map.contains(key)) {
            map[key]!!.value = value
        } else {
            map[key] = CacheNode(key, value, null, null)
        }
        list.pushHead(map[key]!!)
        if (map.size > capacity) {
            list.popTail()?.let { map.remove(it.key) }
        }
    }

    override fun toString() = list.toString()

    private data class CacheNode(
        val key: Int,
        var value: Int,
        var prev: CacheNode?,
        var next: CacheNode?,
    )

    private class DoubleLinkedList {
        var head: CacheNode? = null
            private set
        var tail: CacheNode? = null
            private set

        fun pushHead(newHead: CacheNode) {
            remove(newHead) // remove this node from any previous position in the list
            val oldHead = head
            oldHead?.let { it.prev = newHead }
            newHead.next = oldHead
            if (newHead.next == null) {
                tail = newHead
            }
            head = newHead
        }

        fun pushTail(newTail: CacheNode) {
            remove(newTail) // remove this node from any previous position in the list
            val oldTail = tail
            oldTail?.let { it.next = newTail }
            newTail.prev = oldTail
            if (newTail.prev == null) {
                head = newTail
            }
            tail = newTail
        }

        fun popHead(): CacheNode? {
            val newHead = head?.next
            newHead?.prev = null
            val oldHead = head
            oldHead?.next = null
            head = newHead
            if (head == null) {
                tail = null
            }
            return oldHead
        }

        fun popTail(): CacheNode? {
            val newTail = tail?.prev
            newTail?.next = null
            val oldTail = tail
            oldTail?.prev = null
            tail = newTail
            if (tail == null) {
                head = null
            }
            return oldTail
        }

        fun remove(node: CacheNode) = if (head === node) {
            popHead()
        } else if (tail === node) {
            popTail()
        } else if (node.prev != null && node.next != null) {
            node.prev!!.next = node.next
            node.next!!.prev = node.prev
            node.prev = null
            node.next = null
        } else {
            node.prev = null
            node.next = null
        }

        override fun toString(): String {
            var node = head
            val output = StringBuilder()
            output.append("[")
            var iter = 0
            val limit = 1000
            while (node != null && iter < limit) {
                val prevKey = node.prev?.key ?: "null"
                val nextKey = node.next?.key ?: "null"
                output.append("$prevKey|(k:${node.key}, v:${node.value})|$nextKey")
                if (node !== tail) {
                    output.append(" <-> ")
                }
                node = node.next
                iter++
            }
            output.append("]")
            return output.toString()
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * var obj = LRUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */