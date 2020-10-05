<template>
  <div v-if="project" class="edit-project">
    <h1>Editing Project</h1>
    <h2>{{ oldName }}</h2>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label class="form-label" for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            v-model="project.name"
            name="name"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="description">Description</label>
          <textarea
            type="text"
            class="form-control"
            id="description"
            v-model="project.description"
            name="description"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="status">Staus</label>
          <select
            type="select"
            class="form-control"
            id="status"
            v-model="project.status"
            name="status"
            @change="setStatus($event)"
          >
            <option value="New">New</option>
            <option value="In Progress">In Progress</option>
            <option value="Completed">Completed</option>
          </select>
        </div>

        <div class="form-group">
          <label class="form-label" for="name">Assigned person</label>
          <select
            type="select"
            class="form-control"
            id="person"
            v-model="project.person.id"
            name="name"
          >
            <option v-for="(person, i) in persons" :key="i" :value="person.id">
              {{ person.name }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label class="form-label" for="name">Building</label>
          <select
            type="select"
            class="form-control"
            id="building"
            v-model="project.building.id"
            name="building"
          >
            <option
              v-for="(building, i) in buildings"
              :key="i"
              :value="building.id"
            >
              {{ building.name }}
            </option>
          </select>
        </div>

        <button @click="editProject(project.id)" class="btn btn-success">
          Edit Project
        </button>
      </div>

      <div v-else>
        <h4>Project edited</h4>
        <button class="btn btn-success" @click="newProject">Add</button>
      </div>
    </div>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Project...</p>
  </div>
</template>

<script>
import BuildingDataService from "../../services/BuildingDataService";
import PersonDataService from "../../services/PersonDataService";
import ProjectDataService from "../../services/ProjectDataService";

export default {
  name: "edit-project",
  data() {
    return {
      persons: [],
      buildings: [],
      oldName: "",
      project: null,
      submitted: false,
    };
  },
  methods: {
    async findById(id) {
      await ProjectDataService.findById(id)
        .then((res) => {
          this.project = res.data;
          this.oldName = res.data.name;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    async findAllPersons() {
      await PersonDataService.findAll(null, "page=0&size=200")
        .then((resp) => (this.persons = resp.data.data))
        .catch((e) => console.log(e));
    },

    async findAllBuildings() {
      await BuildingDataService.findAll(null, "page=0&size=200")
        .then((resp) => (this.buildings = resp.data.data))
        .catch((e) => console.log(e));
    },

    async editProject(id) {
      await ProjectDataService.update(id, {
        name: this.project.name,
        description: this.project.description,
        status: this.project.status,
        personId: this.project.person.id,
        buildingId: this.project.building.id
      });
      this.$router.push({ name: "projects" });
    },

    newProject() {
      this.submitted = false;
      this.project = {};
    },
    setStatus(event) {
      this.project.projectStatus = event.target.value;
      console.log("Project: " + this.project.projectStatus);
    },
  },
  mounted() {
    this.findById(this.$route.params.id);
    this.findAllPersons();
    this.findAllBuildings();
  },
};
</script>

<style>
.edit-project {
  padding-top: 50px;
  max-width: 700px;
  margin: auto;
}
.submit-form {
  max-width: 700px;
  margin: auto;
}

.submit-form .form-group {
  padding-top: 10px;
}
</style>