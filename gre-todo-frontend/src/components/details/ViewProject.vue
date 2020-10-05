<template>
  <div v-if="project" class="edit-project">
    <h2>Task: {{ oldName }}</h2>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label class="form-label" for="name">Name</label>
          <input
            disabled
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
            disabled
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
            disabled
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
          <label class="form-label" for="personName">Assigned person</label>
          <input
            disabled
            type="text"
            class="form-control"
            id="personName"
            v-model="project.person.name"
            name="personName"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="buildingName">Building</label>
          <input
            disabled
            type="text"
            class="form-control"
            id="buildingName"
            v-model="project.building.name"
            name="buildingName"
          />
        </div>

        <button @click="editProject(project.id)" class="btn btn-success">
          Edit this Project
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

    editProject(id) {
      this.$router
        .push({ name: "edit-project", params: { id: id } })
        .catch((err) => {
          throw new Error(`An error has occurred: ${err}.`);
        });
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