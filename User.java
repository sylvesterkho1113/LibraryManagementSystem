// Group 09 (Teeple K)
// Library Management System
// 1211211485 Kho Wei Cong
// 1211207735 See Chwan Kai
// 1211208688 Tee Kian Hao
// 1211208756 Tee Chin Yean

public class User extends Person {

    public User(String name, String phone, String email) {
        super();  // Call the constructor of Person
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + "," + phone + "," + email;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        return new User(parts[0], parts[1], parts[2]);
    }
}