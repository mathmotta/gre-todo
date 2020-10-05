import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/",
            name: "home",
            component: () => import("./components/Home")
        },
        {
            path: "/buildings/new",
            name: "new-building",
            component: () => import("./components/create/CreateBuilding")
        },
        {
            path: "/buildings",
            name: "buildings",
            component: () => import("./components/list/BuildingList")
        }
    ]
});
