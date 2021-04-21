package com.tedmo.statemachine;

public class StateMachineContext<S extends Enum<S>, C> {

  private final StateMachine<S, C> stateMachine;
  private final C applicationContext;

  public StateMachineContext(StateMachine<S, C> stateMachine, C applicationContext) {
    this.stateMachine = stateMachine;
    this.applicationContext = applicationContext;
  }

  public S getCurrentState() {
    return stateMachine.getCurrentState();
  }

  public C getApplicationContext() {
    return applicationContext;
  }

  public void transitionTo(S state) {
    stateMachine.transitionTo(state);
  }
}
