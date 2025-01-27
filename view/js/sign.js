const api = "http://localhost:8080/";

document.querySelector("button#login").addEventListener("click", function(){
    submit("login");
})
document.querySelector("button#register").addEventListener("click", function(){
    submit("user");
})

function showErrorMsg(msg){
    let errorSpan = document.querySelector("main").querySelector("h2.error-message");
    errorSpan.innerHTML = msg
}

function submit(type){
    let username = document.getElementById("username-input").value;
    let password = document.getElementById("password-input").value;

    let errorUsername = document.getElementById("username").querySelector("span.error-message");
    let errorPassword = document.getElementById("password").querySelector("span.error-message");;

    if(username === "" || username==null) {
        console.log("Username nao pode ser vazio")
        errorUsername.innerHTML = "Username não pode ser vazio";

        return;
    }else{
        errorUsername.innerHTML = ""
    }

    if(password === ""){
        console.log("Senha não pode ser vazia");
        errorPassword.innerHTML= "Senha não pode ser vazia";

        return;
    }
    else if(password < 8 || password === "" ) {
        console.log("A senha precisa ser maior que 8");
        errorPassword.innerHTML = "A senha precisa ser maior que 8!"

        return;
    }else{
        errorPassword.innerHTML = ""
    }

    sign(type);
}

async function sign(type){
    let username = document.getElementById("username-input").value;
    let password = document.getElementById("password-input").value;

    try {
        if(type === "user") getAPI("user", username, password);
        if(type === "login") getAPI("auth/login", username, password);
    } catch (error) {
        showErrorMsg(error)
    }
}

async function getAPI(setMethod, username, password) {

    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");
    header.append("Accept", "application/json")


    const response = await fetch(`http://localhost:8080/${setMethod}`, {
        method: "POST",
        headers: header,
        body: JSON.stringify({ username, password }),
        }).then(async function(request){
            if (request.ok) {
                const { token } = await request.json();
                header.append("Authorization", token);
                window.localStorage.setItem("Authorization", token);
                console.log(window.localStorage.getItem("Authorization"));    
                window.setTimeout(function () {
                    window.location = "./index.html";
                  }, 2000);
            } 
            else {
            console.log(request.status);

            switch (request.status){
                case 400:
                    showErrorMsg("Bad credentials");
                    break;
                case 401:
                    showErrorMsg("UNAUTHORIZED invalid passoword");
                    break;
                case 403:
                    showErrorMsg("Access denied or authorization error");
                    break;
                case 409:
                    showErrorMsg("Failed to save entity with associated data");
                    break;
                default:
                    showErrorMsg("Unknown error occurred");
            }
            throw new Error('Login failed');
            }
        })   
}
