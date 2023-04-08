package index;

import java.util.Objects;

public class IndexRecord implements Comparable<IndexRecord> {
    private String key;
    private int where;

    public IndexRecord() {}
    public IndexRecord(String key) {
        this.key = key;

    }
    public IndexRecord(String key, int where) {
        this.key = key;
        this.where = where;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }

    @Override
    public String toString() {
        return "index.IndexRecord{" +
                "key=" + key +
                ", where=" + where +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return Objects.equals(key, o);
    }

    public boolean equals(IndexRecord o) {
        if (this == o) return true;
        return Objects.equals(key, o.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, where);
    }

    @Override
    public int compareTo(IndexRecord o) {
        if (key == null)
            return -1;
        return this.key.compareTo(o.key);
    }

    public int compareTo(String o) {
        if (key == null)
            return -1;
        return this.key.compareTo(o);
    }
}
