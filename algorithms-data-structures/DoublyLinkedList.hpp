//============================================================================
// Name        : DoublyLinkedList.hpp
// Author      : Liel Simon
// Version     : 2.0
// Description : Doubly Linked List with Merge Sort
//============================================================================

#ifndef DOUBLY_LINKED_LIST_HPP
#define DOUBLY_LINKED_LIST_HPP

#include <functional>

/**
 * A generic doubly linked list template class
 * Supports any data type with bidirectional traversal
 * Uses lambda predicates and comparators to customize searching, removal, and sorting behavior
 */

template <typename T>
class DoublyLinkedList {

private:
    // Node structure for the doubly linked list
    // Each node stores data and pointers to adjacent nodes
    struct Node {
        // The data stored in this node
        T data;
        // Pointer to the next node 
        Node* next;
        // Pointer to the previous node
        Node* prev;

        // Constructor initializes node with data and null pointers
        Node(const T& value) : data(value), next(nullptr), prev(nullptr) {}
    };

    Node* head;
    Node* tail;
    int size;

    // Helper function that splits list in half for merge sort
    Node* split(Node* head);

    // Helper function that merges two sorted sublists into a single sorted list
    Node* merge(Node* left, Node* right, std::function<bool(const T&, const T&)> comparator);

    // Helper function that recursively sorts using divide and conquer algorithm
    Node* mergeSortRecursive(Node* head, std::function<bool(const T&, const T&)> comparator);

public:
    // Constructor: Initialize empty list
    DoublyLinkedList();

    // Destructor: Free all nodes
    ~DoublyLinkedList();

    // Add element to end of list
    void Append(const T& value);

    // Add element to beginning of list
    void Prepend(const T& value);

    // Remove first element matching predicate
    bool Remove(std::function<bool(const T&)> predicate);

    // Search for element matching predicate
    // Returns: Pointer to element or nullptr if not found
    T* Search(std::function<bool(const T&)> predicate);

    // Sort list using merge sort with custom comparator
    void Sort(std::function<bool(const T&, const T&)> comparator);

    // Apply function to each element in order
    void ForEach(std::function<void(const T&)> func) const;

    // Get number of elements
    int Size() const;

    // Check if list is empty
    bool IsEmpty() const;

    // Remove all elements
    void Clear();
};

//============================================================================
// Implementation
//============================================================================

// Construct empty list template
template <typename T>
DoublyLinkedList<T>::DoublyLinkedList() : head(nullptr), tail(nullptr), size(0) {}

// Destructor clears all nodes template
template <typename T>
DoublyLinkedList<T>::~DoublyLinkedList() {
    Clear();
}

//============================================================================
// Basic list operations 
//============================================================================

template <typename T>
void DoublyLinkedList<T>::Append(const T& value) {
    // Allocate a new node on the heap and store the given value
    Node* newNode = new Node(value);

    // Check if the list is currently empty
    if (head == nullptr) {
        // Empty list: new node becomes both head and tail
        head = newNode;
        tail = newNode;
    }
    else {
        // Link new node after current tail
        tail->next = newNode;
        // Set new node's previous pointer to old tail
        newNode->prev = tail;
        // Update tail to point to the new node
        tail = newNode;
    }
    // Increment list
    ++size;
}

template <typename T>
void DoublyLinkedList<T>::Prepend(const T& value) {
    // Allocate a new node on the heap and store the given value
    Node* newNode = new Node(value);

    if (head == nullptr) {
        // Empty list: new node becomes both head and tail
        head = newNode;
        tail = newNode;
    }
    else {
        // Link new node before current head
        newNode->next = head;
        // Update current head's previous pointer
        head->prev = newNode;
        // Move head to the new node
        head = newNode;
    }

    ++size;
}

template <typename T>
bool DoublyLinkedList<T>::Remove(std::function<bool(const T&)> predicate) {
    // Start from head of the list
    Node* current = head;

    // Traverse list looking for match
    while (current != nullptr) {
        // If current node matches the predicate
        if (predicate(current->data)) {
            // Update previous node's next pointer
            if (current->prev != nullptr) {
                // Bypass current node in forward direction
                current->prev->next = current->next;
            }
            else {
                // Current node is the head
                head = current->next;
            }

            // Update next node's prev pointer
            if (current->next != nullptr) {
                // Bypass current node in backward direction
                current->next->prev = current->prev;
            }
            else {
                // Current node is the tail
                tail = current->prev;
            }

            delete current;
            --size;

            // return a node was removed
            return true;
        }
        // Move to the next node
        current = current->next;
    }
    return false;
}

template <typename T>
T* DoublyLinkedList<T>::Search(std::function<bool(const T&)> predicate) {
    // Start from the head of the list
    Node* current = head;

    // Search through list in order
    while (current != nullptr) {
        // If current node matches predicate
        if (predicate(current->data)) {
            // Return address of the stored data
            return &(current->data);
        }
        current = current->next;
    }

    return nullptr;
}

template <typename T>
void DoublyLinkedList<T>::ForEach(std::function<void(const T&)> func) const {
    Node* current = head;

    // Apply function to each element
    while (current != nullptr) {
        // Run the provided function to the node's data
        func(current->data);
        current = current->next;
    }
}

template <typename T>
int DoublyLinkedList<T>::Size() const {
    // Return the number of nodes currently in the list
    return size;
}

template <typename T>
bool DoublyLinkedList<T>::IsEmpty() const {
    // List is empty if size is zero
    return size == 0;
}

template <typename T>
void DoublyLinkedList<T>::Clear() {
    // Start deletion from the head of the list
    Node* current = head;

    // Delete all nodes
    while (current != nullptr) {
        Node* temp = current;
        current = current->next;
        delete temp;
    }
    // Reset list state to empty
    head = nullptr;
    tail = nullptr;
    size = 0;
}

//============================================================================
// Merge Sort Implementation
//============================================================================

template <typename T>
typename DoublyLinkedList<T>::Node* DoublyLinkedList<T>::split(Node* head) {

    // If list has 0 or 1 nodes, it cannot be split
    if (head == nullptr || head->next == nullptr) {
        return nullptr;
    }
    // Slow pointer advances one node at a time
    Node* slow = head;
    // Fast pointer advances two nodes at a time
    Node* fast = head;

    // Use slow and fast pointer to find middle
    while (fast->next != nullptr && fast->next->next != nullptr) {
        slow = slow->next;
        fast = fast->next->next;
    }

    // Split list at middle
    Node* middle = slow->next;
    slow->next = nullptr;
    if (middle != nullptr) {
        middle->prev = nullptr;
    }

    return middle;
}

template <typename T>
typename DoublyLinkedList<T>::Node* DoublyLinkedList<T>::merge(
    Node* left,
    Node* right,
    std::function<bool(const T&, const T&)> comparator) {

    // If one list is empty, return the other
    if (!left) return right;
    if (!right) return left;

    // Head of the merged list
    Node* result = nullptr;
    // Last node added to the merged list
    Node* current = nullptr;

    // Choose initial node based on comparator
    // If left is the smaller element move left is the head of list
    if (comparator(left->data, right->data)) {
        result = left;
        left = left->next;
    }
    // else start with 'right' and move right forward
    else {
        result = right;
        right = right->next;
    }

    // First node has no previous pointer
    result->prev = nullptr;
    current = result;

    // Merge remaining nodes
    while (left && right) {
        // Choose smaller element according to comparator
        if (comparator(left->data, right->data)) {

            // Append left node
            current->next = left;
            left->prev = current;
            left = left->next;
        }
        else {
            // Append right node
            current->next = right;
            right->prev = current;
            right = right->next;
        }
        // Advance current pointer
        current = current->next;
    }

    // Attach remainder
    if (left) {
        current->next = left;
        left->prev = current;
    }
    else if (right) {
        current->next = right;
        right->prev = current;
    }
    // Return head of merged list
    return result;
}

template <typename T>
typename DoublyLinkedList<T>::Node* DoublyLinkedList<T>::mergeSortRecursive(
    Node* head,
    std::function<bool(const T&, const T&)> comparator) {

    // If the list is empty or contains only one node,
    // it is already sorted and can be returned
    if (head == nullptr || head->next == nullptr) {
        return head;
    }

    // Split list into two halves
    Node* right = split(head);

    // Recursively sort both halves
    head = mergeSortRecursive(head, comparator);

    // Recursively sort the right half of the list.
    right = mergeSortRecursive(right, comparator);

    // Merge sorted halves
    return merge(head, right, comparator);
}

template <typename T>
void DoublyLinkedList<T>::Sort(std::function<bool(const T&, const T&)> comparator) {
    // Empty or single element already sorted
    if (head == nullptr || head->next == nullptr) {
        return;
    }

    // Perform merge sort and update the head pointer
    head = mergeSortRecursive(head, comparator);

    // Update tail pointer by traversing to end
    tail = head;
    while (tail->next != nullptr) {
        tail = tail->next;
    }
}

#endif // DOUBLY_LINKED_LIST_HPP