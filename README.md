# Library Management System

This project, named "library," is developed for educational purposes and is mainly built on Thymeleaf, Spring MVC, and Spring JDBC and others Spring libraries. The system provides functionality for managing people and books within a library setting. Below is a brief overview of the key features:

1. **Entities management:**
   - Pages for adding, editing, and deleting.

2. **Entities Listing:**
   - A page displaying a clickable list of all entities.

3. **Book Return:**
   - On the book details page, if the book is taken by a person, a librarian can click the "Release Book" button to mark the book as returned.

4. **Book Assignment:**
   - On the book details page, if the book is available, a librarian can select a person from a dropdown list and click the "Assign Book" button to assign the book to that person.

5. **Validation:**
   - All fields are validated using `@Valid` and Spring Validator as needed.

## Demo

![Uploading 2023-11-13 22-38-25.gif…]()
