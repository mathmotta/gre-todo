<template>
  <section class="container">
    <div class="left-half">
      <v-img
        alt="GRE Logo"
        class="d-flex align-center mr-2"
        contain
        src="../assets/logo.png"
        @click="goHome()"
      />
    </div>
    <div class="right-half">
      <article>
        <h1>A Sample Application</h1>
        <p>
          A fictional company project that links projects, buildings and people
          :)
        </p>
        <p>
          This application was made for the Equitativa assignment and the full
          source code is available on Github.
        </p>
        <p>
          <v-btn
            class="btn accent"
            href="https://github.com/mathmotta/gre-todo"
            target="_blank"
            text
          >
            <span class="mr-2 mt-2">See project's source code!</span
            ><v-icon class="mt-1">mdi-open-in-new</v-icon>
          </v-btn>
        </p>
      </article>
    </div>
  </section>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {};
  },
};
</script>

<style scoped>
/* Pen-specific styles */
html,
body,
section {
  height: 100%;
}

body {
  color: #fff;
  font-family: sans-serif;
  font-size: 1.4rem;
  line-height: 150%;
  text-align: center;
}

div {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

h1 {
  font-size: 1.75rem;
  margin: 0 0 0.75rem 0;
}

/* Pattern styles */
.container {
  display: flex;
}

.left-half {
  background-color: #fff;
  flex: 1;
  padding: 1rem;
}

.right-half {
  background-color: #0c1248;
  color: #fff;
  flex: 1;
  padding: 3rem;
}
</style>
