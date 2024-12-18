console.log("Script Loaded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () =>{
    changeTheme();
});

// TODO
function changeTheme(){

    changePageTheme(currentTheme, currentTheme);
    //set the listener to change theme button
    const changeThemeButton = document.querySelector("#theme_change_button");

    changeThemeButton.addEventListener("click", (event) => {
        let oldTheme = currentTheme;

        console.log("Change theme button clicked");
        if(currentTheme === "dark"){
            //theme ko light
            currentTheme = "light";
        }else{
            //theme to dark
            currentTheme = "dark";
        }

        changePageTheme(currentTheme, oldTheme);

    });
}    

// set the theme
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

// get theme from localStorage
function getTheme(theme){
    let nowtheme = localStorage.getItem(theme);
    return nowtheme ? theme : "light";
}

function changePageTheme(theme, oldTheme){

    //updating in the local storage
    setTheme(theme);

    // console.log(oldTheme);
    // console.log(theme);

    // remove the current theme
    document.querySelector("html").classList.remove(oldTheme);
    // updating the new theme to the html
    document.querySelector("html").classList.add(theme);

    // change the text of the button
    document.querySelector("#theme_change_button").querySelector("span").textContent = theme == "light" ? "Dark" : "Light";

}