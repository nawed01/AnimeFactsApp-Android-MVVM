package com.nawed.animefacts.models

data class FactsResponse(
    var `data`: List<Data?>?,
    var img: String?,
    var success: Boolean?,
    var total_facts: Int?
)