
const BASE_URL = "http://localhost:8080/task"

document.getElementById('createTaskForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const endAt = document.getElementById('end-at').value;

    createTask(name, description, endAt );
    
    updateTaskTable();
   // this.reset();
});

document.getElementById('deleteTaskForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const taskId = parseInt(document.getElementById('taskId').value);
    deleteTask(taskId);
    updateTaskTable();
    this.reset();
});

async function updateTaskTable() {
    const tbody = document.getElementById('taskTableBody');
    tbody.innerHTML = '';

    const response = await getTasks();

    const data = await response.json();
    let tasks = data;

    tasks.forEach(task => {
        let created = new Date(Date.UTC(task.createdAt[0],  // year
            task.createdAt[1],  // month (0-11)
            task.createdAt[2],  // day
            task.createdAt[3],  // hour
        ))
        
        let end = new Date(Date.UTC(task.endAt[0],  // year
            task.endAt[1],  // month (0-11)
            task.endAt[2],  // day
            task.endAt[3],  // hour
        ))

        const options = { 
            year: 'numeric', 
            month: 'numeric', 
            day: 'numeric',
        };
          

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${created.toLocaleDateString("pt-br", options)}</td>
            <td>${end.toLocaleDateString("pt-br", options)}</td>
        `;
        tbody.appendChild(row);
    });
}

async function getTasks()
{
    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");
    header.append("Authorization", window.localStorage.getItem("Authorization"))

    try {
        const response = await fetch(`${BASE_URL}/user/1`, {
            method: "GET",
            headers: header,
        });

        return response;
    } catch (error) {
        console.error('Network error:', error);
        throw error;
    }
}

async function createTask(title, description, endAt)
{   
    let dateConvert = endAt.split("-").map(Number);
    dateConvert.push(0,0,0,0)
    let raw = JSON.stringify({
        "title": title,
        "description": description,
        "endAt": dateConvert
     });

    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");
    header.append("Authorization", window.localStorage.getItem("Authorization"))

    try {
        const response = await fetch(`${BASE_URL}`, {
            method: "POST",
            headers: header,
            body: raw
        });

    } catch (error) {
        console.error('Network error:', error);
        throw error;
    }
}

async function deleteTask(id)
{   

    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");
    header.append("Authorization", window.localStorage.getItem("Authorization"))

    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: "DELETE",
            headers: header,
        });

    } catch (error) {
        console.error('Network error:', error);
        throw error;
    }
}

renderUserInfo();

async function renderUserInfo() {
    
    const response = await getUser();

    const data = await response.json();

    document.querySelector("#user-name").innerHTML = `User name: ${data.username}`
    document.querySelector("#user-id").innerHTML = `User ID: ${data.id}`
}

async function getUser()
{
    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");
    header.append("Authorization", window.localStorage.getItem("Authorization"))

    try {
        const response = await fetch(`http://localhost:8080/user/me`, {
            method: "GET",
            headers: header,
        });

        return response;
    } catch (error) {
        console.error('Network error:', error);
        throw error;
    }
}

updateTaskTable();