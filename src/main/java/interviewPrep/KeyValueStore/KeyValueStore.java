package interviewPrep.KeyValueStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Entry {
    String value;
    long expiry;

    public Entry(String value, long expiry){
        this.value = value;
        this.expiry = expiry;
    }
}

public class KeyValueStore {
    private Map<String, Entry> store;
    private Map<String, Entry> backup;

    public KeyValueStore() {
        store  = new HashMap<>();
        backup = new HashMap<>();
    }

    public void set(String key, String value) {
        store.put(key,new Entry(value,-1));
    }

    public String get(String key) {

        if(!store.containsKey(key))return null;

        Entry e = store.get(key);
        if(!isExpired(e))return null;

        return e.value;
    }

    public void delete(String key){
        store.remove(key);
    }

    public List<String> getKeysByPrefix(String Prefix){
        List<String> list = new ArrayList<>();
        for(Map.Entry<String,Entry> entry: store.entrySet()){
            Entry e = entry.getValue();
            if(!isExpired(e) && e.value.startsWith(Prefix)){
                list.add(e.value);
            }
        }
        return list;
    }

    public void setWithTTL(String key,String value, long ttl){
        long expiry = System.currentTimeMillis() + ttl;
        store.put(key, new Entry(value,expiry));
    }

    public Boolean isExpired(Entry e){
        if(e.expiry == -1) return false;
        return e.expiry < System.currentTimeMillis();
    }

}
