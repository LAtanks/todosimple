const BASE_URL = "http://localhost:8080";

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
}

// Close modal when clicking outside
document.getElementById('infoOverlay').addEventListener('click', function(e) {
    if (e.target === this) {
        hideError();
    }
});

// Prevent modal close when clicking inside the modal
document.querySelector('.modal').addEventListener('click', function(e) {
    e.stopPropagation();
});

// Close modal with Escape key
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        hideError();
    }
});

function showErrorMessage(){
    const usernameInput = document.querySelector("#username");
    const passwordInput = document.querySelector("#password");

    const usernameError = document.querySelector("div#error-username");
    const passwordError = document.querySelector("div#error-password");

    usernameError.innerHTML = "";
    passwordError.innerHTML = "";

    if(usernameInput.value === '' || usernameInput == null ){
        usernameError.style.display = "block";
        usernameError.innerHTML = "Username não pode ser vazio";
        
        usernameInput.addEventListener("invalid",function (e){
            console.log(e)
        })
        return false;
    }
    else if(usernameInput.value.length < 2){
        usernameError.style.display = "block";
        usernameError.innerText = "Username precisar ser maior que 2 caracteres";

        usernameInput.addEventListener("invalid",function (e){
            console.log(e)
        })
        
        return false;
    }
    else if(usernameInput.value.length > 100){
        usernameError.style.display = "block";
        usernameError.innerText = "Username precisar ser menor que 100 caracteres";
        
        usernameInput.addEventListener("invalid", (e)=>{
            console.log(e)
        })
        return false;
    }

    var passwordvalue =  passwordInput.value;

    if(passwordInput.value === "" || passwordInput == null){
        passwordError.style.display = "block";
        passwordError.innerHTML = "A senha não pode ser vazia";

        return false;
    }
    else if(passwordvalue.length < 8){
        passwordError.style.display = "block";
        passwordError.innerHTML = "A senha tem que ser maior que 8 caratecteres";

        return false;
    }
    else if(passwordvalue.length > 60){
        passwordError.style.display = "block";
        passwordError.innerHTML = "A senha tem que ser menor que 60 caratecteres";

        return false;
    }

    return true;
}
document.getElementById('loginForm').addEventListener('submit', submit);

async function submit(e) {
   //if(!showErrorMessage()) return;

    e.preventDefault();
    
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    console.log(username.value.toString(), password.value.toString())

    // Simple validation
    if(!showErrorMessage()) return;
    
    const response = await loginUser(username.value, password.value);

    if(response.ok){
        //const data = await response.json();
        console.log("User successfully loged in");
        
        const {token} = await response.json();
        console.log(token, response);
        window.localStorage.setItem("Authorization", token);

        console.log(window.localStorage.getItem("Authorization")); 

        window.setTimeout(function () {
           window.location = "../homePage.html";
          }, 2000) 
    }else{
        const data = await response.json();

        showInfo("Error: " + data.message)
        console.error(data);
        return;
    }

    // Here you would typically make an API call to your backend
    console.log('Form submitted:', {
        username: username.value,
        password: password.value
    });
}

document.querySelector(".btn-secondary").addEventListener('click', async function(e) {
    if(!showErrorMessage()) return;

    e.preventDefault();
    
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    
    // Simple validation
    if(!showErrorMessage()) return;
    
    
    const response = await registerUser(username.value, password.value);

    if(response.ok){
        showInfo("User successfully created. Now loged in")
        console.log("User successfully created");

    }else{
        const data = await response.json();

        showInfo("Error: " + data.message)
        console.error(data);
        return;
    }

    // Here you would typically make an API call to your backend
    console.log('Form submitted:', {
        username: username.value,
        password: password.value
    });
});

async function registerUser(username, password){
    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");

    try {
        const response = await fetch(`${BASE_URL}/user`, {
            method: "POST",
            headers: header,
            body: JSON.stringify({ username, password }),
        });

        return response;
    } catch (error) {
        
        showInfo("NetWork error: " + error.message )
        console.error(response);
        throw error;
    }
}

async function loginUser(username, password)
{
    let header = new Headers();
    header.append("Content-Type", "application/json; charset=utf8");

    try {
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: "POST",
            headers: header,
            body: JSON.stringify({ username, password }),
        });

        return response;
    } catch (error) {
        
        showInfo("NetWork error: " + error.message )
        console.error(response);
        throw error;
    }
}

