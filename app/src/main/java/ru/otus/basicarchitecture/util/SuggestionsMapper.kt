package ru.otus.basicarchitecture.util

import ru.otus.basicarchitecture.network.SuggestedAddress

interface Mapper<E, M> {
    fun mapFromEntity(e: E): M
    fun mapFromEntityList(list: List<E>): List<M>
}

object SuggestionsMapper : Mapper<SuggestedAddress, String> {

    override fun mapFromEntity(e: SuggestedAddress): String {
        return e.value
    }
    override fun mapFromEntityList(list: List<SuggestedAddress>): List<String> {
        return list.map { entity -> mapFromEntity(entity) }
    }
}

