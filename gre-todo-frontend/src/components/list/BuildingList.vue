<template>
  <v-row align="center" class="list px-2 mx-auto">
    <v-col cols="12" md="8">
      <v-text-field v-model="name" label="Find by Name"></v-text-field>
    </v-col>

    <v-col cols="12" md="4">
      <v-btn small @click="findBuildings"> Find </v-btn>
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
    };
  },
  methods: {
    async findBuildings() {
      await BuildingDataService.findAll(
        this.name.trim() == "" ? null : this.name.trim(),
        "page=0&size=200"
      )
        .then((resp) => (this.buildings = resp.data.data))
        .catch((e) => console.log(e));
    },

    setActive(building, index) {
      this.currentBuilding = building;
      this.currentIndex = index;
    },

    editBuilding(id) {
      this.$router.push({ name: "building-details", params: { id: id } });
    },

    deleteBuilding(id) {
        console.log('triggered')
      BuildingDataService.delete(id)
        .then(this.findBuildings())
        .catch((e) => console.log(e));
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
</style>
