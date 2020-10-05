<template>
  <v-row align="center" class="list px-2 mx-auto">
    <v-col cols="12" md="12">
      <h1>All Persons</h1>
    </v-col>
    <v-col cols="12" md="8">
      <v-text-field v-model="name" label="Find by Name"></v-text-field>
    </v-col>

    <v-col cols="12" md="2">
      <v-btn small class="btn primary" @click="findPersons"> Find </v-btn>
    </v-col>

    <v-col cols="12" md="2">
      <v-btn small class="btn" @click="resetFilters"> Reset Filter </v-btn>
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
        <v-card-title>Persons</v-card-title>

        <v-data-table
          :headers="headers"
          :items="persons"
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-4" @click="editPerson(item.id)"
              >mdi-lead-pencil</v-icon
            >
            <v-icon small @click="deletePerson(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
    <v-col cols="12" md="2">
      <v-btn class="btn primary" @click="createPerson">
        Create new Person
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import PersonDataService from "../../services/PersonDataService";

export default {
  name: "person-list",
  data() {
    return {
      persons: [],
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
    async findPersons() {
      const params = "page=" + (this.page - 1) + "&size=" + this.pageSize;

      await PersonDataService.findAll(
        this.name.trim() == "" ? null : this.name.trim(),
        params
      )
        .then((resp) => {
          this.persons = resp.data.data;
          this.count = resp.data.total;
        })
        .catch((e) => console.log(e));
    },
    resetFilters() {
      this.name = "";
      this.findPersons();
    },
    handlePageChange(value) {
      this.page = value;
      this.findPersons();
    },

    handlePageSizeChange(event) {
      this.pageSize = event.target.value;
      this.page = 1;
      this.findPersons();
    },

    setActive(person, index) {
      this.currentPerson = person;
      this.currentIndex = index;
    },

    editPerson(id) {
      this.$router
        .push({ name: "edit-person", params: { id: id } })
        .catch((err) => {
          throw new Error(`An error has occurred: ${err}.`);
        });
    },

    createPerson(id) {
      this.$router.push({ name: "new-person", params: { id: id } });
    },

    async deletePerson(id) {
      await PersonDataService.delete(id).catch((e) => console.log(e));
      await this.findPersons();
      this.handlePageChange(1);
    },
  },
  mounted() {
    this.findPersons();
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
