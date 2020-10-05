<template>
  <div v-if="person" class="edit-person">
    <h1>Editing Person: {{ oldName }}</h1>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label class="form-label" for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            required
            v-model="person.name"
            name="name"
          />
        </div>

        <button @click="editPerson(person.id)" class="btn btn-success">
          Edit Person
        </button>
      </div>

      <div v-else>
        <h4></h4>
        <button class="btn btn-success" @click="newPerson">Add</button>
      </div>
    </div>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Person...</p>
  </div>
</template>

<script>
import PersonDataService from "../../services/PersonDataService";

export default {
  name: "edit-person",
  data() {
    return {
      oldName: "",
      person: null,
      submitted: false,
    };
  },
  methods: {
    async findById(id) {
      await PersonDataService.findById(id)
        .then((res) => {
          this.person = res.data;
          this.oldName = res.data.name;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    async editPerson(id) {
      await PersonDataService.update(id, {
        name: this.person.name,
      });
      this.$router.push({ name: "persons" });
    },

    newPerson() {
      this.submitted = false;
      this.person = {};
    },
  },
  mounted() {
    this.findById(this.$route.params.id);
  },
};
</script>

<style>
.edit-person {
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