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
        },
        {
            path: "/buildings/edit/:id",
            name: "edit-building",
            component: () => import("./components/update/UpdateBuilding")
        },
        {
            path: "/persons/new",
            name: "new-person",
            component: () => import("./components/create/CreatePerson")
        },
        {
            path: "/persons",
            name: "persons",
            component: () => import("./components/list/PersonList")
        },
        {
            path: "/persons/edit/:id",
            name: "edit-person",
            component: () => import("./components/update/UpdatePerson")
        }
    ]
});
