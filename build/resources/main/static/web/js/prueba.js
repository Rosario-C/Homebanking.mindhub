// createApp({
//         data() {

//         }
//     }).mount('#app')


    let btn = document.querySelector("#btn");
    let sidebar = document.querySelector(".sidebar");
    
    btn.onclick = function(){
        sidebar.classList.toggle("active");
    }

    // const menu_btn = document.querySelector("#menu-btn")
    // const sidebar = document.querySelector("#sidebar")
    // const container = document.querySelector(".my-container")
    // menu_btn.addEventListener("click",() =>{
    //     sidebar.classList.toggle("active-nav")
    //     container.classList.toggle("active-cont")
    // });