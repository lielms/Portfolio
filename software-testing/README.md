<!--
Hey, thanks for using the awesome-readme-template template.  
If you have any enhancements, then fork this project and create a pull request 
or just open an issue with the label "enhancement".

Don't forget to give this project a star for additional support ;)
Maybe you can mention me or this repo in the acknowledgements too
-->
<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
  * [Tech Stack](#space_invader-tech-stack)
  * [Features](#dart-features)
- [Usage & Diagnostics](#eyes-usage)
- [Testing Stategy](#wrench-testing-strategy)
- [Roadmap](#compass-roadmap)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)

  

<!-- About the Project -->
## :star2: About the Project

Originally developed for CS 320: Software Testing, Automation, and Quality Assurance, this project evolved from a basic CRUD application into a robust, testable diagnostic framework.

The enhancement focuses on "Shift-Left" testing principles by centralizing validation logic, implementing custom exceptions for rich error reporting, and utilizing Dependency Injection to allow for state injection and failure simulation during automated testing.

<!-- TechStack -->
### :space_invader: Tech Stack

<details>
  <summary>Client</summary>
  <ul>
    <li>Java</li>
   <li>JUnit 5</li>
   <li>Parameterized Testing</li>
   <li>Logging API</li>
    <li>Dependency Injection</li>
  </ul>
</details>

<!-- Features -->
### :dart: Features

- Centralized Validation: Unified ContactValidator ensures consistent data integrity across constructors and updates.
- Diagnostic Exceptions: Custom ValidationException provides the specific field and reason for failure, improving debuggability.
- Repository Abstraction: Decoupled storage via ContactRepository interface allows for FailingRepository mocks in edge-case testing.


<!-- Usage -->
## :eyes: Usage

Example of catching diagnostic validation errors:

```javascript
try {
    ContactService service = new ContactService(new ArrayContactRepository());
    service.updateContact("12345", "John", "Doe", "123", "Invalid Phone");
} catch (ValidationException e) {
    // Output: Failed on phoneNumber: Length must be 10
    System.out.println("Failed on " + e.getField() + ": " + e.getReason());
    
    // Metrics tracking for audit
    ValidationMetrics.getFailureCount(); 
}
```

<!-- Testing Strategy -->
## :wrench: Testing Strategy
 * Parameterized Tests: Validates boundary conditions (e.g., exactly 10-digit phones, 30-char addresses) using @MethodSource.
 * Negative Testing: Uses FailingRepository to ensure the service gracefully handles storage failures.
 * Concurrency Testing: ValidationMetrics uses AtomicInteger to ensure thread-safe failure tracking.


<!-- Roadmap -->
## :compass: Roadmap

* [x] Centralize validation into shared validator
* [x] Implement custom ValidationException
* [x] Introduce Repository pattern for testability

<!-- Contact -->
## :handshake: Contact

Liel Simon - liel.simon@snhu.edu

Portfolio: [https://lielms.github.io/](https://lielms.github.io/)


<!-- Acknowledgments -->
## :gem: Acknowledgements

 - SNHU CS 320: Software Testing, Automation, and Quality Assurance
 - readme template : https://github.com/Louis3797/awesome-readme-template/blob/main/README.md

