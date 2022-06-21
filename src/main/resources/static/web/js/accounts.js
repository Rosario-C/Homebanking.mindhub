Vue.createApp({
    data() {
        return {
            nombre: "",
            apellido: "",
            accounts: [],
            loans:[],

        }
    },
    created() {
        
        axios.get("http://localhost:8080/api/clients/current")
            .then(datos =>{
                this.accounts = datos.data.accounts
                this.nombre = datos.data.firstName
                this.apellido = datos.data.lastName

                this.loans = datos.data.loans
                })


    },

    methods: {
        
    signOut(){
        axios.post('/api/logout').then(response =>{ console.log('signed out!!!')
        window.location.href = "/web/index.html"
    })
    },
    newAccount(){
        axios.post('/api/clients/current/accounts')
        .then(response=>location.reload())
        
    }
    },



}).mount('#app')
