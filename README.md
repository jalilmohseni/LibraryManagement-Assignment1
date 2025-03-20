# 📚 Library Management System

This is a **Spring Boot REST API** for a **Library Management System**, allowing management of **members, books, authors, borrow records, and membership cards**.

## 🚀 Features
✔ CRUD operations for **Library Members, Books, Authors, and Borrow Records**  
✔ **Validation** (e.g., no deleting members with borrowed books, no books with future borrow dates)  
✔ **Error handling** for invalid requests  
✔ **Spring Boot, Hibernate, and JPA for database operations**

---

## 🛠️ Technologies Used
- **Java 21** (Spring Boot)
- **Spring Boot **
- **Spring Data JPA**
- **MySQL Database**
- **Lombok**
- **Postman** for API testing

---

## 🏗️ Setup Instructions
- **Clone the repository**
- ** git clone https://github.com/jalilmohseni/LibraryManagement-Assignment1.git
- **Create a MySQL database**
- ** create a database in MySQL named `libraryManagement`**
- **Update the `application.properties` file with your database credentials**
- **Run the application**
- **Test the API using Postman**
- **API Documentation**
- **API Endpoints**
  📡 API Endpoints


📡 API Endpoints

📌 Library Members

Method

Endpoint

Description

GET

/api/members

Get all members

GET

/api/members/{id}

Get a member by ID

POST

/api/members

Add a new member

PUT

/api/members/{id}

Update a member

DELETE

/api/members/{id}

Delete a member (Only if no borrowed books)

📌 Books

Method

Endpoint

Description

GET

/api/books

Get all books

GET

/api/books/{id}

Get a book by ID

POST

/api/books

Add a new book

PUT

/api/books/{id}

Update a book

DELETE

/api/books/{id}

Delete a book (Only if not borrowed)

📌 Authors

Method

Endpoint

Description

GET

/api/authors

Get all authors

GET

/api/authors/{id}

Get an author by ID

POST

/api/authors

Add a new author

PUT

/api/authors/{id}

Update an author

DELETE

/api/authors/{id}

Delete an author (Only if no books)

📌 Borrow Records

Method

Endpoint

Description

GET

/api/borrowRecords

Get all borrow records

GET

/api/borrowRecords/{id}

Get a borrow record by ID

POST

/api/borrowRecords

Add a new borrow record (Validations applied)

PUT

/api/borrowRecords/{id}

Update a borrow record

DELETE

/api/borrowRecords/{id}

Delete a borrow record

🤦🏼‍♂️ Testing with Postman

Open Postman.

Use the provided API endpoints to test GET, POST, PUT, and DELETE requests.

Verify that validation rules prevent incorrect data submission.

📚 License

This project is for educational purposes at SAIT - Object-Oriented Software Development (OOSD) Program.

📌 Contributors

👨‍💻 Jalil Mohseni📍 SAIT - Software Development Program
👨‍💻 Elaheh Vafaeinia📍 SAIT - Software Development Program
👨‍💻 Ryan Angaangan📍 SAIT - Software Development Program
