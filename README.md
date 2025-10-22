1. Description:
   
      A robust back-end service designed to manage user authentication, groups, shared product catalogs, and real-time collaborative shopping lists.
    This API serves as the foundation for a modern web or mobile application where users can plan and track purchases together.

3. Features:
   - User Management: Secure user registration, login, and profile handling.
   - Authentication: JWT-based token management for secure, state-less API access.
   - Group Collaboration: Create private groups for friends or family to share lists.
   - Shared Product Catalog: Centralized product and category definitions (e.g., milk, flour, bread).
   - Shopping Lists: Create and manage multiple shopping lists per group.
   - Real-time Tracking: Features to track item quantity, mark items as checked, and record final purchase prices.
  
4. Project Structure and Technology Stack:
   
      The core development language is Java. All dependencies and the overall build lifecycle are managed by Maven, serving as the central build tool.
     For data persistence, the application utilizes JPA (Java Persistence API), with Hibernate acting as the underlying Object-Relational Mapping (ORM) framework,
     responsible for mapping the Java entities to the database tables. The data is stored in a relational database, which is typically configured to MySQL.
     The application logic is organized around a set of core Entities, which directly reflect the database schema.
