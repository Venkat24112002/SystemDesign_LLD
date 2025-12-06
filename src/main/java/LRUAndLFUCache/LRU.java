package LRUAndLFUCache;

public class LRU {
//    class LRUCache {
//        public:
//        struct Node {
//            int key, val;
//            Node* prev;Node* next;
//            Node (int key, int val) : key(key), val(val), prev(nullptr), next(nullptr) {}
//        };
//
//        public:
//        int cap;
//        unordered_map<int,Node*> cache;
//        Node* latest;
//        Node* oldest;
//
//        LRUCache(int capacity) : cap(capacity) {
//            latest = new Node(0,0);
//            oldest = new Node(0,0);
//            oldest->next = latest;
//            latest->prev = oldest;
//        }
//
//        int get(int key) {
//            if(cache.count(key)) {
//                Node* curr = cache[key];
//                remove(cache[key]);
//                insert(curr);
//                return curr->val;
//            }
//            return -1;
//        }
//
//        private:
//        void remove(Node* node) {
//            Node* prev = node->prev;
//            Node* next = node->next;
//            next->prev = prev;
//            prev->next = next;
//        }
//
//        void insert(Node* node) {
//            Node* prev = latest->prev;
//            Node* next = latest;
//            prev->next = next->prev = node;
//            node->next = next;
//            node->prev = prev;
//        }
//
//        public:
//        void put(int key, int value) {
//            if (cache.find(key) != cache.end()) {
//                remove(cache[key]);
//            }
//            Node* newNode = new Node(key, value);
//            cache[key] = newNode;
//            insert(newNode);
//            if (cache.size() > cap) {
//                Node* lru = oldest->next;
//                remove(lru);
//                cache.erase(lru->key);
//                // delete lru;
//            }
//        }
//    };
}
//
//struct Node {
//int key;int val; int cnt;
//Node* prev; Node* next;
//
//Node(int k,int v) {
//    key = k;
//    val = v;
//    cnt = 0;
//    prev = nullptr, next = nullptr;
//}
//};
//
//struct List {
//int size;
//Node* head;
//Node* tail;
//List() {
//    head = new Node(0,0);
//    tail = new Node(0,0);
//    head->next = tail;
//    tail->prev = head;
//    size = 0;
//}
//
//void addFront(Node *node) {
//    Node* temp = head->next;
//    node->next = temp;
//    node->prev = head;
//    temp->prev = node;
//    head->next = node;
//    size++;
//}
//
//void removeNode(Node* delnode) {
//    Node* delprev = delnode->prev;
//    Node* delnext = delnode->next;
//    delprev->next = delnext;
//    delnext->prev = delprev;
//    size--;
//}
//};
//
//
//
//class LFUCache {
//    map<int,Node*> keyNode;
//    map<int,List*> freqListMap;
//    int maxSizeCache;
//    int minFreq;
//    int currSize;
//
//    public:
//    LFUCache(int capacity) {
//        maxSizeCache = capacity;
//        minFreq=0;
//        currSize=0;
//    }
//
//    void updateFreqList(Node* node) {
//        keyNode.erase(node->key);
//        freqListMap[node->cnt]->removeNode(node);
//        if(node->cnt == minFreq && freqListMap[node->cnt]->size==0) {
//            minFreq++;
//        }
//
//        List* nextHigherFreqList = new List();
//        if(freqListMap.find(node->cnt + 1) != freqListMap.end()) {
//            nextHigherFreqList = freqListMap[node->cnt+1];
//        }
//        node->cnt+=1;
//        nextHigherFreqList->addFront(node);
//        freqListMap[node->cnt] = nextHigherFreqList;
//        keyNode[node->key] = node;
//    }
//
//    int get(int key) {
//        if(keyNode.count(key)) {
//            Node* node = keyNode[key];
//            int val = node->val;
//            updateFreqList(node);
//            return val;
//        }
//        return -1;
//    }
//
//    void put(int key, int value) {
//        if(maxSizeCache==0)return ;
//
//        if(keyNode.count(key)) {
//            Node* node = keyNode[key];
//            node->val = value;
//            updateFreqList(node);
//        }
//        else {
//            if(currSize == maxSizeCache) {
//                List* list = freqListMap[minFreq];
//                keyNode.erase(list->tail->prev);
//                freqListMap[minFreq]->removeNode(list->tail->prev);
//                currSize--;
//            }
//            currSize++;
//            minFreq = 1;
//            List* listFreq = new List();
//            if(freqListMap.find(minFreq)!=freqListMap.end()) {
//                listFreq = freqListMap[minFreq];
//            }
//            Node* node = new Node(key,value);
//            listFreq->addFront(node);
//            keyNode[key] = node;
//            freqListMap[minFreq] = listFreq;
//        }
//    }
//};
