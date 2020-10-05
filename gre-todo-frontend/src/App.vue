<template>
  <v-app>
    <v-app-bar app>
      <div class="d-flex align-center">
        <v-img
          alt="GRE Logo"
          class="d-flex align-center mr-2"
          contain
          src="./assets/logo.png"
          width="300"
          @click="goHome()"
        />
      </div>

      <v-btn to="/buildings" text> Buildings </v-btn>
      <v-btn to="/persons" text> Persons </v-btn>
      <v-btn to="/projects" text> Projects </v-btn>

      <v-spacer></v-spacer>

      <v-btn href="https://github.com/mathmotta/gre-todo" target="_blank" text>
        <span class="mr-2">See project</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data() {
    return {};
  },
  methods: {
    goHome() {
      this.$router.push({ name: "home" });
    },
  },
};
</script>


