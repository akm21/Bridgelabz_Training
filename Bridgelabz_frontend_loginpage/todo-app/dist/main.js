import { getAllTodos } from "./api.js";
async function init() {
    const todos = await getAllTodos();
    console.log("Todos loaded: ", todos);
    //Add your UI rendering logic here
}
init();
