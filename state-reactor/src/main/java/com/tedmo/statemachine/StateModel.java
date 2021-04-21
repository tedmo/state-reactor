package com.tedmo.statemachine;

import java.util.Map;
import java.util.Optional;

public final class StateModel<S extends Enum<S>, C> {

  private final S state;
  private final Map<Class<?>, Action<S, C, ?>> onEventActions;

  public StateModel(S state, Map<Class<?>, Action<S, C, ?>> onEventActions) {
    this.state = state;
    this.onEventActions = onEventActions;
  }

  public S getState() {
    return this.state;
  }

  public <E> Optional<Action<S, C, E>> getOnEventAction(E event) {
    return getAction(event, onEventActions);
  }

  @SuppressWarnings("unchecked")
  private <E> Optional<Action<S, C, E>> getAction(E event, Map<Class<?>, Action<S, C, ?>> actions) {
    if (actions == null || actions.get(event.getClass()) == null) {
      return Optional.empty();
    } else {
      return Optional.of((Action<S, C, E>) actions.get(event.getClass()));
    }
  }

  public static <S extends Enum<S>, C> StateModelBuilder<S, C> builder(
      S state, Class<C> appCtxType) {
    return new StateModelBuilder<>(state);
  }
}
