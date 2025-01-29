
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

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.createdAt[2]}/${task.createdAt[1]}/${task.createdAt[0]}</td>
            <td>${task.endAt[2]}/${task.endAt[1]}/${task.endAt[0]}</td>
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
        showInfo("Erro de conexão")
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
        "endAt": [
            dateConvert[0],
            dateConvert[1],
            dateConvert[2],
            dateConvert[3],
            dateConvert[4],
            dateConvert[5],
            dateConvert[6],
        ]
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
        showInfo("Erro de conexão")
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
        showInfo("Erro de conexão")
        throw error;
    }
}

renderUserInfo();

async function renderUserInfo() {
    
    const response = await getUser();

    const data = await response.json();

    document.querySelector("#user-name").innerHTML = `Nome de usuário: ${data.username}`
    document.querySelector("#user-id").innerHTML = `ID de usuário: ${data.id}`
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
        showInfo("Erro de conexão");
        throw error;
    }
}

function showInfo(message) {
    const overlay = document.getElementById('infoOverlay');
    const infoMessage = document.getElementById('infoMessage');
    
    // Set error message
    infoMessage.textContent = message;
    
    // Show overlay with fade effect
    overlay.style.display = 'flex';
    overlay.style.opacity = '0';
    setTimeout(() => {
        overlay.style.opacity = '1';
    }, 10);
}

// Function to hide error
function hideInfo() {
    const overlay = document.getElementById('infoOverlay');
    overlay.style.opacity = '0';
    setTimeout(() => {
        overlay.style.display = 'none';
    }, 200);

    window.setTimeout(function () {
        window.location = "../signPage.html";
       }, 500) 
}

updateTaskTable();