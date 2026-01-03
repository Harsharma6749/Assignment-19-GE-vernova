CREATE TABLE department (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(50) NOT NULL
);

CREATE TABLE employee (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(100) DEFAULT 'Not Provided',
    dept_id INT NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE payroll (
    pay_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT NOT NULL,
    basic_pay DECIMAL(10,2),
    deduction DECIMAL(10,2),
    taxable_pay DECIMAL(10,2),
    income_tax DECIMAL(10,2),
    net_pay DECIMAL(10,2),
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);

CREATE TABLE emp_dept (
    emp_id INT,
    dept_id INT,
    PRIMARY KEY (emp_id, dept_id),
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

INSERT INTO department (dept_name)
VALUES ('HR'), ('Finance'), ('Sales'), ('Marketing');

INSERT INTO employee (emp_name, phone, address, dept_id)
VALUES 
('Amit', '9876543210', 'Delhi', 1),
('Riya', '9123456780', 'Mumbai', 2),
('John', NULL, DEFAULT, 3);

INSERT INTO payroll (emp_id, basic_pay, deduction, taxable_pay, income_tax, net_pay)
VALUES 
(1, 30000, 2000, 1500, 500, 26000),
(2, 45000, 2500, 2000, 800, 39700);

SELECT e.emp_id, e.emp_name, d.dept_name
FROM employee e
JOIN department d ON e.dept_id = d.dept_id;

SELECT e.emp_name, p.basic_pay, p.net_pay
FROM employee e
JOIN payroll p ON e.emp_id = p.emp_id;

SELECT d.dept_name, COUNT(e.emp_id) AS total_emp
FROM department d
LEFT JOIN employee e ON d.dept_id = e.dept_id
GROUP BY d.dept_name;
