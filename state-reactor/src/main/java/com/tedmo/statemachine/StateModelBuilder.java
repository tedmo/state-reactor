package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;

public class StateModelBuilder<S extends Enum<S>, D> {

  private S state;
  private Map<Class<?>, Action<S, D, ?>> actions;

  protected StateModelBuilder(S state) {
    this.state = state;
    this.actions = new HashMap<>();
  }

  public <E> StateModelBuilder<S, D> on(Class<E> event, Action<S, D, E> action) {
    actions.put(event, action);
    return this;
  }

  public StateModel<S, D> build() {
    return new StateModel<>(state, actions);
  }
}
