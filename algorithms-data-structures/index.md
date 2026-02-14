---
title: Algorithm & Data Structures Artifact Enhancement
nav_order: 2
---

# Algorithm & Data Structures Artifact Enhancement

<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
  * [Tech Stack](#space_invader-tech-stack)
  * [Features](#dart-features)
- [Usage](#eyes-usage)
- [Roadmap](#compass-roadmap)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)

  

<!-- About the Project -->
## :star2: About the Project

This project is a data structures assignment that I have improved upon. Originally developed as a Singly Linked List for CS 300, this artifact has been enhanced into a Generic Doubly Linked List library.

The system is designed to handle large datasets (specifically bid data from CSV files) with high efficiency. By implementing a recursive Merge Sort algorithm, the project demonstrates advanced algorithmic principles, moving beyond linear traversal to achieve \(O(n \log n)\) performance.

<!-- TechStack -->
### :space_invader: Tech Stack

<details>
  <summary>Client</summary>
  <ul>
    <li>C++</li>
   <li>Standard Template Library (STL)</li>
   <li>CSVParser for data ingestion</li>
  </ul>
</details>

<!-- Features -->
### :dart: Features

- Generic Templates: The DoublyLinkedList<T> class supports any data type, not just Bids.
- Bi-directional Traversal: Uses prev and next pointers for flexible navigation.
- Efficient Sorting: Integrated Merge Sort providing \(O(n \log n)\) time complexity.\
- Robust CRUD: Support for appending, searching, and safe removal of nodes.


<!-- Usage -->
## :eyes: Usage

Example of how to use the templated list in your C++ code:

```javascript
// Initialize the list
DoublyLinkedList<Bid> bidList;

// Add data
bidList.Append(newBid);

// Sort using a lambda comparator
bidList.Sort([](const Bid& a, const Bid& b) {
    return a.amount < b.amount;
});

// Search for a specific record
Bid* found = bidList.Search([&id](const Bid& b) {
    return b.bidId == id;
});
```

<!-- Roadmap -->
## :compass: Roadmap

* [x] Refactor Singly to Doubly Linked List
* [x] Implement Class Templates
* [x] Integrate Recursive Merge Sort
* [ ] Add Iterator Support (C++ STL style)


<!-- Contact -->
## :handshake: Contact

Liel Simon - liel.simon@snhu.edu

Project Link: [https://lielms.github.io/](https://lielms.github.io/)


<!-- Acknowledgments -->
## :gem: Acknowledgements

 - SNHU CS 300: Data Structures and Algorithms, Module 3 Linked List Assignment
 - readme template : https://github.com/Louis3797/awesome-readme-template/blob/main/README.md


