const apiUrl = "http://localhost:8080/task/user/6";

function show(tasks) {
    let tab = `<thead>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Description</th>
          </thead>`;
  
    for (let task of tasks) {
      tab += `
              <tr>
                  <td scope="row">${task.id}</td>
                  <td scope="row">${task.name}</td>
                  <td>${task.description}</td>
              </tr>
          `;
    }
  
    document.getElementById("tasks").innerHTML = tab;
  }

async function getAPI() {
    console.log(window.localStorage.getItem("Authorization"));

    const response = await fetch(apiUrl, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem("Authorization"),
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
        }),
        })/*.then(async function(request){
            if (request.ok) {    
                
            }
            else {
                console.log(request.status);
                    switch (request.status){
                        case 400:
                            console.log("Bad credentials");
                            break;
                        case 401:
                            console.log("UNAUTHORIZED invalid passoword");
                            break;
                        case 403:
                            console.log("Access denied or authorization error");
                            break;
                        case 409:
                            console.log("Failed to save entity with associated data");
                            break;
                        default:
                            console.log("Unknown error occurred");
                    }
                    throw new Error('Login failed');
                }
                
        }) */
        var data = await response.json();
        show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "./sign.html";
});
  

getAPI();

