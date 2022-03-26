# Lab 03 - Multi-layer application testing (with Spring Boot)



### 3.1 Employee manager example

##### Review Questions:

##### a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

​	AssertJ is a Java library that provides a rich set of assertions and truly helpful error messages, improves test code readability

On ***A_EmployeeRepositoryTest.java*** we have this example:

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName)
    .containsOnly(alex.getName(), ron.getName(), bob.getName());
```

On ***D_EmployeeRestControllerIT.java*** we have this example:

```java
assertThat(found).extracting(Employee::getName).containsOnly("bob");
```




##### b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

​	As explained on the Labs PDF the ***B_EmployeeService_UnitTest.java*** mocks the behavior of the repository in the *setUp()* function:

```java
Employee john = new Employee("john", "john@deti.com");
john.setId(111L);

Employee bob = new Employee("bob", "bob@deti.com");
Employee alex = new Employee("alex", "alex@deti.com");

List<Employee> allEmployees = Arrays.asList(john, bob, alex);

Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
```



##### c) What is the difference between standard @Mock and @MockBean?

​	With `@Mock` we instruct Mockito to create a mock as we don't want a *real* instance of a class. Mockito's JUnit Jupiter extension will then take care to instantiate the mock and inject it to our class under test.

​	On the other hand, we can use the `@MockBean` to add *mock* objects to the Spring application context. The mock will replace any existing bean of the same type in the application context. It's useful in integration tests where a particular bean, like an external service, needs to be mocked. 



##### d) What is the role of the file `application-integrationtest.properties`? In which conditions will it be used?

​	We can see some specifications, such as how the connection to the database is made by the Spring Boot application. In this case, when the `@TestPropertySource` annotation is used in the integration tests, it uses the real database information for the tests.



##### e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?   

​	

// TODO
