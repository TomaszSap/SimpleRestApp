This is simple REST Application that represents CRUD operations on object Person.
This object have validations on every variable which are "name", "surname", "email",
"mobileNumber" (9 digits) and "PESEL" (11 digits).
Endpoints and their descriptions:
- http://localhost:8088/api/add accepts a JSON which represents person
- http://localhost:8088/api/updateById?id= - updates the person with the given id
- http://localhost:8088/api/deleteById?id= - removes the person with the given id
- http://localhost:8088/api/getAll -returns list of persons
- http://localhost:8088/api/getSortedBy?sortBy={how} 
returns sorted list of persons ,argument {how} can take values:
name,surname,email
- http://localhost:8088/api/allRequests - displays number of all requests that were sent to the API 

