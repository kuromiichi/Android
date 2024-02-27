package dev.kuromiichi.examenfirebase.ui.recyclers.listeners

import dev.kuromiichi.examenfirebase.models.Event

interface EventOnClickListener {
    fun onClick(event: Event)
}