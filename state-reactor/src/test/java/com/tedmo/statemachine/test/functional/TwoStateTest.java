package com.tedmo.statemachine.test.functional;

import static org.junit.jupiter.api.Assertions.*;

import com.tedmo.statemachine.Action;
import com.tedmo.statemachine.StateMachine;
import com.tedmo.statemachine.StateMachineContext;
import com.tedmo.statemachine.StateMachineModel;
import com.tedmo.statemachine.StateModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwoStateTest {

  StateMachineModel<BusinessState, BusinessObject> stateMachineModel;
  TransitionToTerminalAction transitionToTerminalAction;

  @BeforeEach
  public void setup() {
    transitionToTerminalAction = new TransitionToTerminalAction();

    var stateModel =
        StateModel.builder(BusinessState.INITIAL, BusinessObject.class)
            .on(TransitionEvent.class, transitionToTerminalAction)
            .build();

    var stateModels = Arrays.asList(stateModel);
    this.stateMachineModel =
        new StateMachineModel<BusinessState, BusinessObject>(BusinessState.INITIAL, stateModels);
  }

  @Test
  void testTransition() {
    BusinessObject appCtx = new BusinessObject();
    var stateMachine = new StateMachine<>(stateMachineModel, appCtx);

    // Verify the app context is empty before sending state machine event
    assertTrue(appCtx.getTestLog().isEmpty());

    // Verify state machine is in intial state
    assertEquals(BusinessState.INITIAL, stateMachine.getCurrentState());

    // Send event
    String eventMessage = "TEST EVENT MESSAGE";
    TransitionEvent transitionEvent = new TransitionEvent(eventMessage);
    stateMachine.sendEvent(transitionEvent);

    // Verify state transition
    assertEquals(BusinessState.TERMINAL, stateMachine.getCurrentState());

    // Verify app context was updated
    assertEquals(1, appCtx.getTestLog().size());
    assertEquals(eventMessage, appCtx.getTestLog().get(0));
  }

  enum BusinessState {
    INITIAL,
    TERMINAL
  }

  class BusinessObject {
    private List<String> testLog;

    public BusinessObject() {
      testLog = new ArrayList<>();
    }

    private void addToLog(String message) {
      testLog.add(message);
    }

    public List<String> getTestLog() {
      return testLog;
    }
  }

  static class TransitionEvent {
    private String eventMessage;

    public TransitionEvent(String eventMessage) {
      this.eventMessage = eventMessage;
    }

    public String getEventMessage() {
      return eventMessage;
    }
  }

  static class TransitionToTerminalAction
      implements Action<BusinessState, BusinessObject, TransitionEvent> {

    @Override
    public void doAction(
        StateMachineContext<BusinessState, BusinessObject> ctx, TransitionEvent event) {
      ctx.getApplicationContext().addToLog(event.getEventMessage());
      ctx.transitionTo(BusinessState.TERMINAL);
    }
  }
}
