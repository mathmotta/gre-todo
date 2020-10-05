<template>
  <v-row align="center" class="list px-2 mx-auto">
        <v-col cols="12" md="12">
      <h1>All Buildings</h1>
    </v-col>
    <v-col cols="12" md="8">
      <v-text-field v-model="name" label="Find by Name"></v-text-field>
    </v-col>
    <v-col cols="12" md="2">
      <v-btn small class="btn primary" @click="findBuildings"> Find </v-btn>
    </v-col>
        <v-col cols="12" md="2">
      <v-btn small class="btn" @click="resetFilters"> Reset filter </v-btn>
    </v-col>
    <v-col cols="12" md="4">
      <h4>Total: {{count}}</h4>
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
        <v-card-title>Buildings</v-card-title>

        <v-data-table
          :headers="headers"
          :items="buildings"
          disable-pagination
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-4" @click="editBuilding(item.id)"
              >mdi-pencil</v-icon
            >
            <v-icon small @click="deleteBuilding(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
    <v-col cols="12" md="2">
      <v-btn class="btn primary" @click="createBuilding">
        Create new Building
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import BuildingDataService from "../../services/BuildingDataService";

export default {
  name: "building-list",
  data() {
    return {
      buildings: [],
      name: "",
      headers: [
        { text: "Name", align: "start", sortable: true, value: "name" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      pageSizes: [5, 10, 50],
      page: 1,
      count: 0,
      pageSize: 5,
    };
  },
  methods: {
    async findBuildings() {
      const params = "page=" + (this.page - 1) + "&size=" + this.pageSize;

      await BuildingDataService.findAll(
        this.name.trim() == "" ? null : this.name.trim(),
        params
      )
        .then((resp) => {
          this.buildings = resp.data.data;
          this.count = resp.data.total;
        })
        .catch((e) => console.log(e));
    },

    resetFilters(){
        this.name = "";
        this.findBuildings()
    },

    handlePageChange(value) {
      this.page = value;
      this.findBuildings();
    },

    handlePageSizeChange(event) {
      this.pageSize = event.target.value;
      this.page = 1;
      this.findBuildings();
    },

    setActive(building, index) {
      this.currentBuilding = building;
      this.currentIndex = index;
    },

    editBuilding(id) {
      this.$router
        .push({ name: "edit-building", params: { id: id } })
        .catch((err) => {
          throw new Error(`An error has occurred: ${err}.`);
        });
    },

    createBuilding(id) {
      this.$router.push({ name: "new-building", params: { id: id } });
    },

    async deleteBuilding(id) {
      await BuildingDataService.delete(id)
        .catch((e) => console.log(e));
      await this.findBuildings();
      this.handlePageChange(1)
    },
  },
  mounted() {
    this.findBuildings();
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
    color: #0C1248 !important;  
}
.page-item.active .page-link {  
    background-color: #0C1248 !important;  
    border-color: #0C1248 !important;  
    color: #fff !important;  
}
</style>
