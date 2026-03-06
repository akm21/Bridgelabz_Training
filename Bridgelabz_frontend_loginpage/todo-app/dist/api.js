const BASE_URL = "http://localhost:3001/todos";
//GET all todos
export async function getAllTodos() {
    const response = await fetch(BASE_URL);
    return response.json();
}
//POST a new todo 
export async function createTodo(data) {
    const response = await fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    });
    return response.json();
}
//PATCH to update a todo
export async function updateTodo(id, data) {
    const response = await fetch(`${BASE_URL}/${id}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    });
    return response.json();
}
//DELETE a todo
export async function deleteTodo(id) {
    await fetch(`${BASE_URL}/${id}`, {
        method: "DELETE",
    });
}
