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

## 📌 Library Members

### Method

- Get all members

/api/members

- Get a member by ID

/api/members/{id}

- POST

/api/members

Add a new member

- PUT

/api/members/{id}

Update a member

- DELETE

/api/members/{id}

- Delete a member (Only if no borrowed books)

## 📌 Books

### Method


- Get all books

/api/books

- Get a book by ID

/api/books/{id}
 
- POST

/api/books

Add a new book

- PUT

/api/books/{id}

Update a book

- DELETE

/api/books/{id}

Delete a book (Only if not borrowed)

## 📌 Authors

### Method


- Get all authors

/api/authors

- Get an author by ID

/api/authors/{id}

- POST

/api/authors

Add a new author

- PUT

/api/authors/{id}

Update an author

- DELETE

/api/authors/{id}

Delete an author (Only if no books)

## 📌 Borrow Records

### Method

- GET all borrow records
- 
/api/borrowRecords

- Get a borrow record by ID

/api/borrowRecords/{id}

- POST

/api/borrowRecords

Add a new borrow record (Validations applied)

- PUT

/api/borrowRecords/{id}

Update a borrow record

- DELETE

/api/borrowRecords/{id}

Delete a borrow record

## 🤦 Testing with Postman

Open Postman.

Use the provided API endpoints to test GET, POST, PUT, and DELETE requests.

Verify that validation rules prevent incorrect data submission.

## 📌 Contributors

####  👨‍💻 Jalil Mohseni📍 SAIT - Software Development Program
#### 👨‍💻 Elaheh Vafaeinia📍 SAIT - Software Development Program
#### 👨‍💻 Taofeek Oduola 📍 SAIT - Software Development Program
