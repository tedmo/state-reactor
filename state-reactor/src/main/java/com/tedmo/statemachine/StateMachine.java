package com.tedmo.statemachine;

/**
 * @param <S> State enum
 * @param <C> User defined context type
 */
public final class StateMachine<S extends Enum<S>, C> {

  private final StateMachineContext<S, C> ctx;
  private final StateMachineModel<S, C> model;
  private S currentState;

  public StateMachine(StateMachineModel<S, C> model, C appCtx) {
    this(model, appCtx, model.getInitialState());
  }

  public StateMachine(StateMachineModel<S, C> model, C appCtx, S currentState) {
    this.ctx = new StateMachineContext<>(this, appCtx);
    this.model = model;
    this.currentState = currentState;
  }

  public S getCurrentState() {
    return currentState;
  }

  public C getApplicationContext() {
    return ctx.getApplicationContext();
  }

  public <E> void sendEvent(E event) {
    model.getOnEventAction(currentState, event).ifPresent(action -> action.doAction(ctx, event));
  }

  protected void transitionTo(S state) {
    this.currentState = state;
  }
}
