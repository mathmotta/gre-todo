package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.security.SecurityProvider;
import com.goldenrealstate.gretodo.business.service.impl.PersonService;
import com.goldenrealstate.gretodo.business.service.impl.ProjectService;
import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.common.ProjectStatus;
import com.goldenrealstate.gretodo.data.filter.ProjectSpecification;
import com.goldenrealstate.gretodo.data.model.Building;
import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.model.Project;
import com.goldenrealstate.gretodo.data.repository.IBuildingRepository;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import com.goldenrealstate.gretodo.data.repository.IProjectRepository;
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
 * Tests the default person service {@link PersonService}
 * This should NOT spy/autowire interface services.
 *
 * @author Mathews Motta
 * @since 1.0
 */
@SuppressWarnings("ALL")
public class ProjectServiceTest {

    @InjectMocks
    @Spy
    private ProjectService projectService;

    @Mock
    private IProjectRepository projectRepository;

    @Mock
    private IPersonRepository personRepository;

    @Mock
    private IBuildingRepository buildingRepository;

    @Mock
    private SecurityProvider securityProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createProjectWithoutPersonAndBuildingAndReturnSuccess() throws InvalidNameException {
        String projectName = "Cleanup the garbage left on Bifröst";
        String projectDescription = "It's probably Loki's fault";
        ProjectStatus projectStatus = ProjectStatus.NEW;

        ProjectRepresentation pr = mock(ProjectRepresentation.class);
        doReturn(projectName).when(pr).getName();
        doReturn(projectDescription).when(pr).getDescription();
        doReturn(projectStatus.toString()).when(pr).getStatus();

        doReturn("TestUser").when(securityProvider).getCurrentUser();
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.create(pr);

        assertThat(result).isNotNull();
        assertThat(result.getPerson()).isNull();
        assertThat(result.getBuilding()).isNull();
    }

    @Test
    public void createProjectWithoutBuildingAndReturnSuccess() throws InvalidNameException {
        String projectName = "Cleanup the garbage left on Bifröst";
        String projectDescription = "It's probably Loki's fault";
        ProjectStatus projectStatus = ProjectStatus.NEW;
        long personId = 1;

        ProjectRepresentation pr = mock(ProjectRepresentation.class);
        doReturn(projectName).when(pr).getName();
        doReturn(projectDescription).when(pr).getDescription();
        doReturn(projectStatus.toString()).when(pr).getStatus();
        doReturn(personId).when(pr).getPersonId();

        doReturn("TestUser").when(securityProvider).getCurrentUser();
        Person p = mock(Person.class);
        doReturn(Optional.of(p)).when(personRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.create(pr);

        assertThat(result).isNotNull();
        assertThat(result.getPerson()).isNotNull();
        assertThat(result.getBuilding()).isNull();
    }

    @Test
    public void createProjectAndReturnSuccess() throws InvalidNameException {
        String projectName = "Cleanup the garbage left on Bifröst";
        String projectDescription = "It's probably Loki's fault";
        ProjectStatus projectStatus = ProjectStatus.NEW;
        long personId = 1;
        long buildingId = 1;

        ProjectRepresentation pr = mock(ProjectRepresentation.class);
        doReturn(projectName).when(pr).getName();
        doReturn(projectDescription).when(pr).getDescription();
        doReturn(projectStatus.toString()).when(pr).getStatus();
        doReturn(personId).when(pr).getPersonId();
        doReturn(buildingId).when(pr).getBuildingId();

        doReturn("TestUser").when(securityProvider).getCurrentUser();
        Person p = mock(Person.class);
        doReturn(Optional.of(p)).when(personRepository).findById(anyLong());
        Building b = mock(Building.class);
        doReturn(Optional.of(b)).when(buildingRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.create(pr);

        assertThat(result).isNotNull();
        assertThat(result.getPerson()).isNotNull();
        assertThat(result.getBuilding()).isNotNull();
    }

    @Test
    public void createProjectAndFailBecauseOfNullName() {
        ProjectRepresentation pr = mock(ProjectRepresentation.class);
        doReturn(null).when(pr).getName();
        assertThatThrownBy(() -> projectService.create(pr)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void createProjectAndFailBecauseOfEmptyName() {
        ProjectRepresentation pr = mock(ProjectRepresentation.class);
        doReturn("").when(pr).getName();
        assertThatThrownBy(() -> projectService.create(pr)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void updateProjectNameAndReturnSuccess() throws IdNotFoundException {
        String projectName = "Cleanup the garbage left on Bifröst";

        Project prResult = new Project();
        prResult.setName(projectName);

        String projectNameUpdated = "Cleanup the trash left on Bifröst"; // garbage with trash
        ProjectRepresentation prMock = mock(ProjectRepresentation.class);
        doReturn(projectNameUpdated).when(prMock).getName();

        doReturn(Optional.of(prResult)).when(projectRepository).findById(anyLong());
        doReturn("TestUser").when(securityProvider).getCurrentUser();
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.update(1L, prMock);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(projectNameUpdated);
    }

    @Test
    public void updateProjectDescriptionAndReturnSuccess() throws IdNotFoundException {
        String projectDescription = "It's probably Loki's fault";

        Project prResult = new Project();
        prResult.setDescription(projectDescription);

        String descriptionUpdated = "It's probably Thor's fault"; // Loki with Thor
        ProjectRepresentation prMock = mock(ProjectRepresentation.class);
        doReturn(descriptionUpdated).when(prMock).getDescription();

        doReturn(Optional.of(prResult)).when(projectRepository).findById(anyLong());
        doReturn("TestUser").when(securityProvider).getCurrentUser();
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.update(1L, prMock);

        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo(descriptionUpdated);
    }

    @Test
    public void updateProjectStatusAndReturnSuccess() throws IdNotFoundException {
        ProjectStatus projectStatus = ProjectStatus.NEW;

        Project prResult = new Project();
        prResult.setProjectStatus(projectStatus);

        ProjectStatus newStatus = ProjectStatus.IN_PROGRESS;
        ProjectRepresentation prMock = mock(ProjectRepresentation.class);
        doReturn(newStatus.toString()).when(prMock).getStatus();

        doReturn(Optional.of(prResult)).when(projectRepository).findById(anyLong());
        doReturn("TestUser").when(securityProvider).getCurrentUser();
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.update(1L, prMock);

        assertThat(result).isNotNull();
        assertThat(result.getProjectStatus()).isEqualTo(newStatus);
    }

    @Test
    public void updateProjectPersonAndReturnSuccess() throws IdNotFoundException {
        Person person = mock(Person.class);

        Project prResult = new Project();
        prResult.setPerson(person);

        long newPersonId = 1L;
        ProjectRepresentation prMock = mock(ProjectRepresentation.class);
        doReturn(newPersonId).when(prMock).getPersonId();


        doReturn(Optional.of(prResult)).when(projectRepository).findById(anyLong());
        doReturn("TestUser").when(securityProvider).getCurrentUser();
        Person newPerson = mock(Person.class);
        doReturn(Optional.of(newPerson)).when(personRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.update(1L, prMock);

        assertThat(result).isNotNull();
        assertThat(result.getPerson()).isEqualTo(newPerson);
    }

    @Test
    public void updateProjectBuildingAndReturnSuccess() throws IdNotFoundException {
        Building building = mock(Building.class);

        Project prResult = new Project();
        prResult.setBuilding(building);

        long newBuildingId = 1L;
        ProjectRepresentation prMock = mock(ProjectRepresentation.class);
        doReturn(newBuildingId).when(prMock).getBuildingId();


        doReturn(Optional.of(prResult)).when(projectRepository).findById(anyLong());
        doReturn("TestUser").when(securityProvider).getCurrentUser();
        Building newBuilding = mock(Building.class);
        doReturn(Optional.of(newBuilding)).when(buildingRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(projectRepository).save(any(Project.class));
        Project result = projectService.update(1L, prMock);

        assertThat(result).isNotNull();
        assertThat(result.getBuilding()).isEqualTo(newBuilding);
    }

    @Test
    public void updateProjectAndReturnFailBecauseIdNotFound() {
        doReturn(Optional.empty()).when(projectRepository).findById(anyLong());
        assertThatThrownBy(() -> projectService.update(1L,  mock(ProjectRepresentation.class))).isInstanceOf(IdNotFoundException.class);
    }

    @Test
    public void findAllProjectsAndReturnSuccess() {
        Project project1 = mock(Project.class);
        Project project2 = mock(Project.class);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        doReturn(new PageImpl<>(List.of(project1, project2), pageRequest, 10)).when(projectRepository).findAll(Pageable.unpaged());

        Page<Project> results = projectService.findAll(Pageable.unpaged());

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(project1);
        assertThat(results.getContent()).contains(project2);
    }

    @Test
    public void findProjectsByFilterAndReturnSuccess() {
        doReturn(Page.empty()).when(projectRepository).findAll(any(ProjectSpecification.class), any(Pageable.class));
        projectService.findByFilter(mock(ProjectRepresentation.class), Pageable.unpaged());
    }
}
