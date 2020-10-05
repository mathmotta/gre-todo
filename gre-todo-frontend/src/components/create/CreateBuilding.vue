<template>
  <div class="create-building">
    <h1>Create a Building</h1>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            required
            v-model="building.name"
            name="name"
          />
        </div>

        <button @click="createBuilding" class="btn btn-success">Create building</button>
      </div>

      <div v-else>
        <h4>Building submitted</h4>
        <button class="btn btn-success" @click="newBuilding">Add</button>
      </div>
    </div>
  </div>
</template>

<script>
import BuildingDataService from '../../services/BuildingDataService'

export default {
  name: 'create-building',
  data() {
    return {
      building: {
        name: '',
      },
      submitted: false,
    };
  },
  methods: {
    async createBuilding() {
      await BuildingDataService.create({
        name: this.building.name,
      });
      this.$router.push({ name: "buildings" });
    },

    newBuilding() {
      this.submitted = false
      this.building = {}
    },
  },
};
</script>

<style>
.create-building {
  padding-top: 50px;
  max-width: 700px;
  margin: auto;
}
.submit-form {
  max-width: 700px;
  margin: auto;
}
.submit-form .form-group{
  padding-top: 20px;
}
</style>