package com.snim.core.writing

import com.snim.core.JsonUtils
import org.springframework.core.convert.converter.Converter

class WritingData2JsonConverter : Converter<WritingData, String> {
    override fun convert(source: WritingData) = JsonUtils.Object2Json(source)
}

class Json2WritingDataConverter : Converter<String, WritingData> {
    override fun convert(source: String) = JsonUtils.Json2Object(source, WritingData::class.java)
}