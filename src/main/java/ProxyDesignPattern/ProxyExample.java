package ProxyDesignPattern;

interface Employee {
  void get(String client);
}

class EmployeeImpl implements Employee {
    String query;

    public EmployeeImpl(String query){
        this.query = query;
    }

    @Override
    public void get(String client){
        System.out.println("getting data" + query);
    }


}

class EmployeeProxy implements Employee{

    EmployeeImpl employee;

    public EmployeeProxy(String query) {
        employee = new EmployeeImpl(query);
    }

    @Override
    public void get(String client){
        if(client.equals("ADMIN")){
            employee.get(client);
        } else {
            System.out.println("not avaialable");
        }
    }


}

public class ProxyExample {

    public static void main(String[] args){
        Employee employeeProxy = new EmployeeProxy("data for 1 st dataset");
        employeeProxy.get("USER");
    }
}
