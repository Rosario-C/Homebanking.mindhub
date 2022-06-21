Vue.createApp({
    data() {
        return {
            email: "",
            password: "",
            newEmail: "",
            newName: "",
            newLastname: "",
            newPassword: "",

        }
    },

    created() {




    },

    methods: {
        signIn() {
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }).then(response => {
                console.log('signed in!!!')
                window.location.href = "/web/accounts.html"
            })
        },
        signOut() {
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href = "/web/index.html"
            })
        },
        signUp() {
            axios.post('/api/clients', `firstName=${this.newName}&lastName=${this.newLastname}&email=${this.newEmail}&password=${this.newPassword}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }).then(response => {this.email =this.newEmail, this.password = this.newPassword,
                this.signIn()
                .then(response => {console.log("registred&logged")
                window.location.href = "/web/accounts.html"
            })
        })
        }


    },

}).mount('#app')

let x =document.getElementById("login");
let y = document.getElementById("signup");
let z = document.getElementById("btn");

function signup(){
    x.style.left = "-400px";
    y.style.left = "50px";
    z.style.left = "110px";
}
function login(){
    x.style.left = "50px";
    y.style.left = "450px";
    z.style.left = "0";
}
