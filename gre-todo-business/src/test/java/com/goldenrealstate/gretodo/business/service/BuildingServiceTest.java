package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.impl.BuildingService;
import com.goldenrealstate.gretodo.data.model.Building;
import com.goldenrealstate.gretodo.data.repository.IBuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests the default building service {@link BuildingService}
 * This should NOT spy/autowire interface services.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class BuildingServiceTest {

    @InjectMocks
    @Spy
    private BuildingService buildingService;

    @Mock
    private IBuildingRepository buildingRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createBuildingAndReturnSuccess() throws InvalidNameException {
        String buildingName = "Valhalla";
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        Building result = buildingService.create(buildingName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(buildingName);
    }

    @Test
    public void createBuildingAndFailBecauseOfNullName() {
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        assertThatThrownBy(() -> buildingService.create(null)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void createBuildingAndFailBecauseOfEmptyName() {
        String buildingName = "";
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        assertThatThrownBy(() -> buildingService.create(buildingName)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void updateBuildingAndReturnSuccess() throws InvalidNameException, IdNotFoundException {
        String buildingName = "Bifröst";
        doReturn(Optional.of(mock(Building.class))).when(buildingRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        Building result = buildingService.update(1L, buildingName);

        assertThat(result).isNotNull();
    }

    @Test
    public void updateBuildingAndReturnFailBecauseOfNullName() {
        doReturn(Optional.of(mock(Building.class))).when(buildingRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        assertThatThrownBy(() -> buildingService.update(1L, null)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void updateBuildingAndReturnFailBecauseOfEmptyName() {
        String buildingName = "";
        doReturn(Optional.of(mock(Building.class))).when(buildingRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(buildingRepository).save(any(Building.class));
        assertThatThrownBy(() -> buildingService.update(1L, buildingName)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void findAllBuildingsAndReturnSuccess() {
        Building building1 = mock(Building.class);
        Building building2 = mock(Building.class);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        doReturn(new PageImpl<>(List.of(building1, building2), pageRequest, 10)).when(buildingRepository).findAll(Pageable.unpaged());

        Page<Building> results = buildingService.findAll(Pageable.unpaged());

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(building1);
        assertThat(results.getContent()).contains(building2);
    }

    @Test
    public void findPersonsByNameAndReturnSuccess() {
        String buildingName = "Fólkvangr";
        Building building = mock(Building.class);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        doReturn(new PageImpl<>(List.of(building), pageRequest, 10)).when(buildingRepository).findByName(anyString(), any());
        Page<Building> results = buildingService.findByName(buildingName, Pageable.unpaged());

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(building);
    }
}
