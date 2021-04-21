package com.tedmo.statemachine;

public interface Action<S extends Enum<S>, C, E> {

  void doAction(StateMachineContext<S, C> ctx, E event);
}
