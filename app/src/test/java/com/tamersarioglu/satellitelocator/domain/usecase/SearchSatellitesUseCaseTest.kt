package com.tamersarioglu.satellitelocator.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import org.junit.Before
import org.junit.Test

class SearchSatellitesUseCaseTest {

    private lateinit var useCase: SearchSatellitesUseCase
    private lateinit var mockSatellites: List<Satellite>

    @Before
    fun setUp() {
        useCase = SearchSatellitesUseCase()
        mockSatellites = listOf(
            Satellite(1, "Starship-1", true),
            Satellite(2, "Dragon-1", false),
            Satellite(3, "Starship-3", true),
            Satellite(4, "Falcon-Heavy", false),
            Satellite(5, "Crew Dragon", true)
        )
    }

    @Test
    fun `invoke with empty query returns all satellites`() {
        val result = useCase(mockSatellites, "")
        assertThat(result).isEqualTo(mockSatellites)
        assertThat(result).hasSize(5)
    }

    @Test
    fun `invoke with blank query returns all satellites`() {
        val result = useCase(mockSatellites, "   ")
        assertThat(result).isEqualTo(mockSatellites)
        assertThat(result).hasSize(5)
    }

    @Test
    fun `invoke with query filters satellites correctly`() {
        val result = useCase(mockSatellites, "Starship")
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("Starship-1")
        assertThat(result[1].name).isEqualTo("Starship-3")
    }

    @Test
    fun `invoke is case insensitive`() {
        val result = useCase(mockSatellites, "DRAGON")
        assertThat(result).hasSize(2)
        assertThat(result.map { it.name }).containsExactly("Dragon-1", "Crew Dragon")
    }

    @Test
    fun `invoke with no matches returns empty list`() {
        val result = useCase(mockSatellites, "NotFound")
        assertThat(result).isEmpty()
    }

    @Test
    fun `invoke trims whitespace in query`() {
        val result = useCase(mockSatellites, "  Dragon  ")
        assertThat(result).hasSize(2)
        assertThat(result.map { it.name }).containsExactly("Dragon-1", "Crew Dragon")
    }
}