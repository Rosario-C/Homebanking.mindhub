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
        
        axios.get("/api/clients/current")
            .then(datos =>{
                this.accounts = datos.data.accounts
                this.nombre = datos.data.firstName
                this.apellido = datos.data.lastName

                this.loans = datos.data.loans
                })


    },

    methods: {
        
    signOut(){
        axios.post('/api/logout').then(response =>{ 
        window.location.href = "/web/index.html"
    })
    },
    newAccount(){
        axios.post('/api/clients/current/accounts')
        .then(response=>location.reload())
        
    }
    },



}).mount('#app')
