
<template>
  <div class="create-project">
    <h1>Create a Project</h1>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            required
            v-model="project.name"
            name="name"
          />
        </div>

        <div class="form-group">
          <label for="name">Description</label>
          <textarea
            type="textarea"
            class="form-control"
            id="description"
            required
            v-model="project.description"
            name="description"
          />
        </div>

        <div class="form-group">
          <label for="name">Status</label>
          <select
            type="select"
            class="form-control"
            id="status"
            required
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
          <label for="name">Assigned person</label>
          <select
            type="select"
            class="form-control"
            id="person"
            required
            v-model="project.personId"
            name="person"
          >
            <option v-for="(person, i) in persons" :key="i" :value="person.id">
              {{ person.name }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label for="name">Building</label>
          <select
            type="select"
            class="form-control"
            id="building"
            required
            v-model="project.buildingId"
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

        <button @click="createProject" class="btn btn-success">
          Create project
        </button>
      </div>

      <div v-else>
        <h4>Project submitted.</h4>
        <button class="btn btn-success" @click="newProject">Add</button>
      </div>
    </div>
  </div>
</template>

<script>
import BuildingDataService from "../../services/BuildingDataService";
import PersonDataService from "../../services/PersonDataService";
import ProjectDataService from "../../services/ProjectDataService";

export default {
  name: "create-project",
  data() {
    return {
      persons: [],
      buildings: [],
      defaultStatus: "New",
      project: {
        name: "",
        description: "",
        status: "New",
        personId: null,
        buildingId: null,
      },
      submitted: false,
    };
  },
  methods: {
    async createProject() {
      await ProjectDataService.create({
        name: this.project.name,
        description: this.project.description,
        status: this.project.status,
        personId: this.project.personId,
        buildingId: this.project.buildingId,
      });
      this.$router.push({ name: "projects" });
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

    setStatus(event) {
      this.project.status = event.target.value;
    },

    newProject() {
      this.submitted = false;
      this.project = {};
    },
  },
  mounted() {
    this.findAllPersons();
    this.findAllBuildings();
  },
};
</script>

<style>
.create-project {
  padding-top: 50px;
  max-width: 700px;
  margin: auto;
}
.submit-form {
  max-width: 700px;
  margin: auto;
}
.submit-form .form-group {
  padding-top: 20px;
}
</style>