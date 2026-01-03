create database payroll_service;
use payroll_service;

create table employee_payroll (
    id int auto_increment primary key,
    name varchar(50),
    salary double,
    start date
);

insert into employee_payroll (name, salary, start)
values
('Bill', 100000, '2018-01-03'),
('Mark', 200000, '2019-11-13'),
('Charlie', 300000, '2020-05-21');

select * from employee_payroll;

select salary from employee_payroll
where name = 'Bill';

select * from employee_payroll
where start between cast('2018-01-01' as date) and date(now());

alter table employee_payroll
add gender char(1);

update employee_payroll
set gender = 'M'
where name = 'Bill' or name = 'Charlie';

update employee_payroll
set gender = 'F'
where name = 'Mark';

select
    gender,
    sum(salary) as total_salary,
    avg(salary) as avg_salary,
    min(salary) as min_salary,
    max(salary) as max_salary,
    count(*) as emp_count
from employee_payroll
group by gender;
