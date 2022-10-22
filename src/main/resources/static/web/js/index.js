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
            axios.post('/api/login', `email=${this.email}&password=${this.password}`
            ).then(response => {
                window.location.href = "/web/accounts.html"
            })
        },
        signOut() {
            axios.post('/api/logout').then(response => {
                window.location.href = "/web/index.html"
            })
        },
        signUp() {
            axios.post('/api/clients', `firstName=${this.newName}&lastName=${this.newLastname}&email=${this.newEmail}&password=${this.newPassword}`

            ).then(response => {this.email =this.newEmail, this.password = this.newPassword
             
                .then(response => {
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
