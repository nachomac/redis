# spring-boot-redis-rest

**AVAILABLE ENDPOINTS**

| method            | resource          | description                                                                                   |
|:------------------|:------------------|:----------------------------------------------------------------------------------------------|
| `GET`			| `/users`		| get the collection of users -> 200(OK)														|
| `GET`			| `/users/:id`	| get a student by Id -> 200(OK), 400(wrong id format), 404(no student with such id)					|
| `POST`			| `/users`		| creates a student in the DB -> 201(created)														|
| `PUT`			| `/users/:id`	| updates a student in the DB -> 204(updated), 400(wrong id format), 404(no student with such id)		|
| `DELETE`		| `/users/:id`	| deletes a student from the DB -> 204(deleted), 400(wrong id format), 404(no student with such id)	|

example request of posting a student:
{
    "id":"67",
    "name":"pep",
    "email":"pep@pep.com"
}