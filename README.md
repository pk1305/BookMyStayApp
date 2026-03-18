Hotel Booking Management System

Use Case 1: Application Entry & Welcome Message.

This Java program represents the entry point of the Hotel Booking Management System.
It starts the application and prints a welcome message confirming that the system has been initialized successfully.

Use Case 2: Basic Room Types


New!Click to edit & Static Availability

This use case demonstrates basic object-oriented concepts in Java by modeling different hotel room types such as Single Room, Double Room, and Suite Room. The system creates room objects and displays their details along with their availability using simple variables.

The goal of this use case is to introduce abstraction, inheritance, polymorphism, and encapsulation while representing hotel room information in a structured way.

Use Case 3: Centralized Room Inventory Management
This use case introduces centralized inventory management in the Hotel Booking Management System. Room availability is managed using a HashMap that maps room types to the number of available rooms.

The goal of this use case is to replace scattered availability variables with a single data structure, ensuring consistent updates and efficient room availability management.

Use Case 4: Room Search & Availability Check
This use case allows guests to search for available rooms and view their details without modifying the system state. The system retrieves availability information from the centralized inventory and displays only the room types that have available rooms.

The goal of this use case is to implement safe, read-only access to room availability while maintaining a clear separation between search functionality and booking operations.
# Use Case 6: Booking Allocation

## Goal
Process booking requests and allocate rooms based on availability.

## Actor
- Booking Processor
- Room Inventory

## Flow
- Requests are taken from queue.
- System checks room availability.
- If available → booking confirmed.
- If not → request rejected.
- Inventory is updated accordingly.

## Key Concepts
- Queue processing
- HashMap (inventory management)
- Condition checking
- State update (inventory)

## Benefits
- Ensures correct room allocation
- Prevents overbooking
- Maintains consistency in inventory