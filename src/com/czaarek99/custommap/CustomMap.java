package com.czaarek99.custommap;

public class CustomMap<K, V> {

    private static final int DEFAULT_TABLE_CAPCITY = 8;

    private Node<K, V>[] table = new Node[DEFAULT_TABLE_CAPCITY];
    private int tableCapacity = DEFAULT_TABLE_CAPCITY;
    private int size = 0;

    private int hash(K key) {
        return key.hashCode() & (this.tableCapacity - 1);
    }

    private void realloc(int newCapacity) {
        this.tableCapacity = newCapacity;

        Node<K, V>[] oldTable = this.table.clone();

        this.table = new Node[newCapacity];
        this.size = 0;

        for(int i = 0; i < oldTable.length; i++) {
            Node<K, V> node = oldTable[i];

            while(node != null) {
                this.put(node.key, node.value);

                node = node.next;
            }
        }
    }

    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    public Node<K, V> get(K key) {
        int hash = this.hash(key);
        Node<K, V> node = this.table[hash];

        if(node != null) {
            while(node != null) {
                if(node.key.equals(key)) {
                    return node;
                }

                node = node.next;
            }
        }

        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(K key, V value) {
        if(this.size == this.tableCapacity) {
            this.realloc(this.tableCapacity * 2);
        }

        int hash = this.hash(key);

        if(table[hash] == null) {
            table[hash] = new Node<>(hash, key, value);
        } else {
            Node<K, V> node = table[hash];

            while(true) {
                if(node.key.equals(key)) {
                    node.setValue(value);
                    return;
                }

                if(node.next == null) {
                    break;
                }

                node = node.next;
            }

            node.setNext(new Node<>(hash, key, value));
        }

        this.size++;
    }

    public void remove(K key) {
        int hash = this.hash(key);

        Node<K, V> node = table[hash];
        if(node != null) {
            if(node.key.equals(key)) {
               if(node.next == null)  {
                   table[hash] = null;
               } else {
                   table[hash] = node.next;
               }

                this.size--;
            } else {
                while(node != null) {

                    if(node.next.key.equals(key)) {
                        node.setNext(node.next.next);
                        this.size--;
                        break;
                    }

                    node = node.next;
                }
            }
        }

        if(this.size < this.tableCapacity / 4) {
            this.realloc(this.tableCapacity / 2);
        }
    }

    public int getSize() {
        return this.size;
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        Node(int hash, K key, V value) {
            this(hash, key, value, null);
        }

        void setNext(Node<K, V> next) {
            this.next = next;
        }

        void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node(key: " + key + ", val: " + this.value + ")";
        }
    }

}
