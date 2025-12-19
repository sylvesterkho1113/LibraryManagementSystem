// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

// Person base class of User >> set default value of variable
public class Person {
    public String name;
    public String phone;
    public String email;

    public Person() {
        this.name = "John Doe";
        this.phone = "000";
        this.email = "0@0.com";
    }

    public String getName() {
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String email() {
        return email;
    }
}

