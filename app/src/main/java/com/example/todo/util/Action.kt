package com.example.todo.util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun String?.toAction(): Action {
    Action.values().forEach { action ->
        if (action.toString() == this) {
            return action
        }
    }
    return Action.NO_ACTION
}