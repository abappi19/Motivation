package io.github.abappi19.motivation.calendar.domain

import android.annotation.SuppressLint
import io.github.abappi19.motivation.core.domain.WidgetConfig
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CalendarWidgetConfig(
    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate? = null,
    val pastConfig: DotConfig? = null,
    val todayConfig: DotConfig? = null,
    val futureConfig: DotConfig? = null,
    val backgroundColor: Long? = null,
    val dotSize: Float? = null
): WidgetConfig

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class DotConfig(
    val fillColor: Long? = 0xffffff,
    val borderColor: Long? = 0xffffff,
    val roundness: Float? = 1f
)

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDate) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(decoder.decodeString())
}
