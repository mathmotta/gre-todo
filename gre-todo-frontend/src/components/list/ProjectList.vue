<template>
  <v-row align="center" class="list px-2 mx-auto">
    <v-col cols="12" md="12">
      <h1>All Projects</h1>
    </v-col>
    <v-col cols="12" md="5">
      <v-text-field v-model="filter.name" label="Find by Name"></v-text-field>
    </v-col>
    <v-col cols="12" md="4">
      <v-select
        v-model="filter.status"
        :items="statuses"
        item-text="statusName"
        item-value="value"
        label="Find by Status"
      />
    </v-col>
    <v-col cols="12" md="3">
      <v-btn small @click="resetFilters"> Reset filters </v-btn>
    </v-col>
    <v-col cols="12" md="5">
      <v-select
        v-model="filter.personId"
        :items="persons"
        item-text="name"
        item-value="id"
        label="Find by Person"
      />
    </v-col>
    <v-col cols="12" md="5">
      <v-select
        v-model="filter.buildingId"
        :items="buildings"
        item-text="name"
        item-value="id"
        label="Find by Building"
      />
    </v-col>

    <v-col cols="12" md="2">
      <v-btn small class="btn primary" @click="findProjectsByFilter">
        Find
      </v-btn>
    </v-col>

    <v-col cols="12" md="4">
      <h4>Total: {{ count }}</h4>
    </v-col>

    <v-col cols="12" md="8">
      <b-pagination
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        align="right"
        size="m"
        class="mb-0"
        variant="primary"
        @change="handlePageChange"
      >
      </b-pagination>
    </v-col>
    <v-col cols="12" sm="12">
      <v-card class="mx-auto" tile>
        <v-card-title>Projects</v-card-title>

        <v-data-table
          :headers="headers"
          :items="projects"
          disable-pagination
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="viewProject(item.id)"
              >mdi-text-box-search</v-icon
            >
            <v-icon small class="mr-2" @click="editProject(item.id)"
              >mdi-lead-pencil</v-icon
            >
            <v-icon small class="mr-2" @click="deleteProject(item.id)"
              >mdi-delete</v-icon
            >
          </template>
        </v-data-table>
      </v-card>
    </v-col>
    <v-col cols="12" md="2">
      <v-btn class="btn primary" @click="createProject">
        Create new Project
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import BuildingDataService from "../../services/BuildingDataService";
import PersonDataService from "../../services/PersonDataService";
import ProjectDataService from "../../services/ProjectDataService";

export default {
  name: "project-list",
  data() {
    return {
      projects: [],
      persons: [],
      buildings: [],
      filter: {
        name: null,
        status: null,
        personId: null,
        buildingId: null,
      },
      headers: [
        { text: "Name", align: "start", sortable: false, value: "name" },
        { text: "Status", sortable: false, value: "status" },
        { text: "Person", sortable: false, value: "person.name" },
        { text: "Building", sortable: false, value: "building.name" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      statuses: [
        { statusName: "New", value: "New" },
        { statusName: "In Progress", value: "In Progress" },
        { statusName: "Completed", value: "Completed" },
      ],
      pageSizes: [5, 10, 50],
      page: 1,
      count: 0,
      pageSize: 5,
    };
  },
  methods: {
    getParams() {
      return "page=" + (this.page - 1) + "&size=" + this.pageSize;
    },
    async findProjects() {
      const params = this.getParams();

      await ProjectDataService.findAll(params)
        .then((resp) => {
          this.projects = resp.data.data;
          this.count = resp.data.total;
        })
        .catch((e) => console.log(e));
    },
    async findProjectsByFilter() {
      const params = this.getParams();
      this.verifyFilter();
      await ProjectDataService.findByFilter(params, this.filter)
        .then((resp) => {
          this.projects = resp.data.data;
          this.count = resp.data.total;
        })
        .catch((e) => console.log(e));
    },
    verifyFilter() {
      if (!this.filter.name || /^\s*$/.test(this.filter.name))
        this.filter.name = null;
    },

    resetFilters() {
      this.filter = {
        name: null,
        status: null,
        personId: null,
        buildingId: null,
      };
      this.findProjectsByFilter();
    },

    handlePageChange(value) {
      this.page = value;
      this.findProjectsByFilter();
    },

    handlePageSizeChange(event) {
      this.pageSize = event.target.value;
      this.page = 1;
      this.findProjectsByFilter();
    },

    setActive(project, index) {
      this.currentProject = project;
      this.currentIndex = index;
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
    viewProject(id) {
      this.$router
        .push({ name: "view-project", params: { id: id } })
        .catch((err) => {
          throw new Error(`An error has occurred: ${err}.`);
        });
    },
    editProject(id) {
      this.$router
        .push({ name: "edit-project", params: { id: id } })
        .catch((err) => {
          throw new Error(`An error has occurred: ${err}.`);
        });
    },

    createProject(id) {
      this.$router.push({ name: "new-project", params: { id: id } });
    },

    async deleteProject(id) {
      await ProjectDataService.delete(id).catch((e) => console.log(e));
      await this.findProjects();
      this.handlePageChange(1);
    },
  },
  mounted() {
    this.findProjects();
    this.findAllPersons();
    this.findAllBuildings();
  },
};
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
.text-align {
  margin-left: 30px;
}
.page-item .page-link {
  background-color: #fff !important;
  color: #0c1248 !important;
}
.page-item.active .page-link {
  background-color: #0c1248 !important;
  border-color: #0c1248 !important;
  color: #fff !important;
}
</style>
