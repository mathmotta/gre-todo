<template>
  <div class="create-person">
    <h1>Create a Person</h1>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            required
            v-model="person.name"
            name="name"
          />
        </div>

        <button @click="createPerson" class="btn btn-success">
          Create person
        </button>
      </div>

      <div v-else>
        <h4>Person submitted</h4>
        <button class="btn btn-success" @click="newPerson">Add</button>
      </div>
    </div>
  </div>
</template>

<script>
import PersonDataService from "../../services/PersonDataService";

export default {
  name: "create-person",
  data() {
    return {
      person: {
        name: "",
      },
      submitted: false,
    };
  },
  methods: {
    async createPerson() {
      await PersonDataService.create({
        name: this.person.name,
      });
      this.$router.push({ name: "persons" });
    },

    newPerson() {
      this.submitted = false;
      this.person = {};
    },
  },
};
</script>

<style>
.create-person {
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