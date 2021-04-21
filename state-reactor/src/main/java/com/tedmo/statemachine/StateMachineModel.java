package com.tedmo.statemachine;

import java.util.*;

public final class StateMachineModel<S extends Enum<S>, C> {

  private Map<S, StateModel<S, C>> stateModels;
  private S initialState;

  public StateMachineModel(S initialState, List<StateModel<S, C>> stateModels) {
    Map<S, StateModel<S, C>> stateModelMap = new HashMap<>();
    for (StateModel<S, C> stateModel : stateModels) {
      S state = stateModel.getState();
      if (stateModelMap.containsKey(stateModel.getState())) {
        throw new StateMachineModelException(
            "State " + state + " cannot be added to the model more than once");
      }
      stateModelMap.put(stateModel.getState(), stateModel);
    }
    this.stateModels = stateModelMap;
    this.initialState = initialState;
  }

  public S getInitialState() {
    return initialState;
  }

  public <E> Optional<Action<S, C, E>> getOnEventAction(S state, E event) {
    var stateModel = stateModels.get(state);
    if (stateModel != null) {
      return stateModel.getOnEventAction(event);
    }
    return Optional.empty();
  }
}
