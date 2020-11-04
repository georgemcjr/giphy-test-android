package br.com.gj.giphytest.model

sealed class State {
    class Success<out T>(val content: T) : State() {
        inline fun <reified T> safeContent() = content as T
    }
    class Error(val error: Throwable) : State()
    object Loading : State()
}