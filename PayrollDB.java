import java.sql.*;
import java.util.*;

class Employee {
    int id;
    String name;
    double salary;
    String gender;
    String start;

    Employee(int id, String name, double salary, String gender, String start) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.start = start;
    }
}

public class PayrollDB {

    String url = "jdbc:mysql://localhost:3306/payroll_service";
    String user = "root";
    String pass = "root";
    Connection con;

    public Connection connect() throws Exception {
        con = DriverManager.getConnection(url, user, pass);
        return con;
    }

    public List<Employee> getAll() throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee_payroll";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    rs.getString("gender"),
                    rs.getString("start")
            ));
        }
        return list;
    }

    public void updateSalary(String name, double sal) throws Exception {
        String sql = "UPDATE employee_payroll SET salary = ? WHERE name = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setDouble(1, sal);
        pst.setString(2, name);
        pst.executeUpdate();
    }

    public List<Employee> findByDate(String s, String e) throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN ? AND ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, s);
        pst.setString(2, e);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    rs.getString("gender"),
                    rs.getString("start")
            ));
        }
        return list;
    }

    public void genderStats() throws Exception {
        String sql = "SELECT gender, SUM(salary), AVG(salary), MIN(salary), MAX(salary), COUNT(*) FROM employee_payroll GROUP BY gender";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String g = rs.getString(1);
            double sum = rs.getDouble(2);
            double avg = rs.getDouble(3);
            double min = rs.getDouble(4);
            double max = rs.getDouble(5);
            int cnt = rs.getInt(6);

            System.out.println(g + ": " + sum + " " + avg + " " + min + " " + max + " " + cnt);
        }
    }

    public static void main(String[] args) {
        try {
            PayrollDB db = new PayrollDB();
            db.connect();

            List<Employee> all = db.getAll();
            for (Employee e : all)
                System.out.println(e.id + " " + e.name);

            db.updateSalary("Terisa", 3000000);

            List<Employee> dr = db.findByDate("2020-01-01", "2022-12-31");
            for (Employee e : dr)
                System.out.println(e.name + " " + e.start);

            db.genderStats();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
