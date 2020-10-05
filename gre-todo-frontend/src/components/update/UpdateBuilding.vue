<template>
  <div v-if="building" class="edit-building">
    <h1>Editing Building: {{ oldName }}</h1>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label class="form-label" for="name">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            required
            v-model="building.name"
            name="name"
          />
        </div>

        <button @click="editBuilding(building.id)" class="btn btn-success">
          Edit Building
        </button>
      </div>

      <div v-else>
        <h4>Building edited!</h4>
        <button class="btn btn-success" @click="newBuilding">Add</button>
      </div>
    </div>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Building...</p>
  </div>
</template>

<script>
import BuildingDataService from "../../services/BuildingDataService";

export default {
  name: "edit-building",
  data() {
    return {
      oldName: "",
      building: null,
      submitted: false,
    };
  },
  methods: {
    async findById(id) {
      await BuildingDataService.findById(id)
        .then((res) => {
          this.building = res.data
          this.oldName = res.data.name;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    async editBuilding(id) {
      await BuildingDataService.update(id, {
        name: this.building.name,
      });
      this.$router.push({ name: "buildings" });
    },

    newBuilding() {
      this.submitted = false;
      this.building = {};
    },
  },
  mounted() {
    this.findById(this.$route.params.id);
  },
};
</script>

<style>
.edit-building {
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