# state-reactor

This library is a sort of hybrid implementation of the state pattern and a state machine.  The intention is to provide a simple way of reacting to events based on the current state of some domain model.

#### Goals
- Manage the state of a domain object/context through events
- Perform actions triggered by events based on current state 
- Handle events with different payloads while maintaining type safety
- Simple hydration of a new state machine instance
- State machine model that can be shared across multiple instances of the state machine
- Event handlers that can be shared across multiple instances of the state machine
