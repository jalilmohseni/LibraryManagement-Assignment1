# 📚 Library Management System (Spring Boot + JWT + RBAC)

This is a secure RESTful Library Management System built with **Spring Boot 3**, **Spring Security**, **JWT Authentication**, and **Role-Based Access Control (RBAC)**. It manages library members, books, authors, borrow records, and user authentication.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based Login & Registration
- BCrypt password hashing
- Role-Based Access Control (RBAC)
- Supports 3 roles:
    - **ADMIN**: Full access
    - **LIBRARIAN**: Manage books/authors/members
    - **MEMBER**: Borrow and return books

### 📦 Modules
- **Library Members** (`/api/members`)
- **Membership Cards** (`/api/membership-cards`)
- **Books** (`/api/books`)
- **Authors** (`/api/authors`)
- **Borrow Records** (`/api/borrowRecords`)
- **Authentication** (`/api/auth`)

---

## 🔑 Roles & Permissions

| Role       | Capabilities |
|------------|--------------|
| **ADMIN**  | Manage librarians (users), authors, books, and view all borrow records |
| **LIBRARIAN** | Add/edit/delete books/authors, manage borrow records, view members |
| **MEMBER** | Borrow/return books, view own borrow history |
| **Public** | Can view available books and authors (no auth required) |

---

## 🧪 API Endpoints (Sample)

### 🔐 Auth
| Endpoint           | Method | Access       |
|--------------------|--------|--------------|
| `/api/auth/login`  | POST   | Public       |
| `/api/auth/register` | POST | Public       |
| `/api/auth/logout` | POST   | Authenticated |

### 📘 Books
| Endpoint         | Method | Role             |
|------------------|--------|------------------|
| `/api/books`     | GET    | Public           |
| `/api/books`     | POST   | ADMIN, LIBRARIAN |
| `/api/books/{id}`| PUT/DELETE | ADMIN, LIBRARIAN |

### 👥 Members
| Endpoint           | Method | Role         |
|--------------------|--------|--------------|
| `/api/members`     | GET    | ADMIN, LIBRARIAN |
| `/api/members/{id}`| GET    | ADMIN, LIBRARIAN, MEMBER |
| `/api/members`     | POST/PUT | LIBRARIAN  |
| `/api/members/{id}`| DELETE | ADMIN       |

---

## 🛡️ Security

- JWT tokens are used for securing requests.
- Add your JWT to the `Authorization` header as:
- Authorization: Bearer <your_token>


---

## 💾 Tech Stack

- Java 17+
- Spring Boot 3
- Spring Security
- JJWT (JSON Web Tokens)
- MySQL / H2 (Your preferred DB)
- Maven

---

## 🧰 Getting Started

### 🧑‍💻 Prerequisites

- Java 17+
- Maven
- MySQL running locally (or update application.properties)

### ⚙️ Run the Project

```bash
# 1. Clone the project
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system

# 2. Build and run
mvn spring-boot:run
```


### 🔐 Sample Users
Role	Username	Password
Admin	admin	admin123
Librarian	lib	lib123
Member	Register using /api/auth/register	


| Role                | Username | Password  |
|---------------------|----------|-----------|
| `Admin`             | admin    | admin123  |
| `Librarian`         | lib      | lib123    |
| `Member`            | Register using /api/auth/register | LIBRARIAN |

## 📌 Contributors

####  👨‍💻 Jalil Mohseni📍 SAIT - Software Development Program
#### 👨‍💻 Elaheh Vafaeinia📍 SAIT - Software Development Program
#### 👨‍💻 Taofeek Oduola📍 SAIT - Software Development Program
