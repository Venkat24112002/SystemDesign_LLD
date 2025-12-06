package AccessManagement;

import java.util.HashMap;
import java.util.Map;

public class AllInOneFile {

    public enum AccessLevel{
        NO_ACCESS,
        READ,
        WRITE
    };

    public class Employee{

        final private int id;
        private String name;

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
    };

    public class Resource{
        final private int id;
        private String name;

        public Resource(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
    };

    public class AccessManager {
        private Map<Integer, Map<Integer, AccessLevel>> empToResAccess;
        private Map<Integer, Map<Integer, AccessLevel>> resToEmpAccess;

        public AccessManager() {
            empToResAccess = new HashMap<>();
            resToEmpAccess = new HashMap<>();
        }

        public void grantAccess(Employee employee, Resource resource, AccessLevel accessLevel) {
            int empId = employee.getId();
            int resId = resource.getId();

//            empToResAccess.computeIfAbsent(empId, k->new HashMap<>()).
//                    put(resId, accessLevel);

            if(!empToResAccess.containsKey(empId)){
                empToResAccess.put(empId, new HashMap<>());
            }
            empToResAccess.get(empId).put(resId,accessLevel);

        }

        public void getAccess(Employee employee, Resource resource) {
            int empId = employee.getId();
            int resId = resource.getId();

            empToResAccess.get(empId).get(resId);

//            Comparator<String> cmp = (s1,s2) ->Integer.compare(s1.length(),s2.length())
//            PriorityQueue<Person> pqDesc = new PriorityQueue<>(
//                    (p1, p2) -> Integer.compare(p2.getAge(), p1.getAge())
//            );
        }


    }

    public static void main(String[] args) {

    }

}
