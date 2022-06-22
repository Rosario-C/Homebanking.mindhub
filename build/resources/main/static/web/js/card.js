Vue.createApp({
    data() {
        return {
            nombre: "",
            apellido: "",
            tarjetas: "",
            type:"",
            color:"",
            accounts: [],
            expired:"",



        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(datos => {

                this.nombre = datos.data.firstName
                this.apellido = datos.data.lastName
                this.tarjetas = datos.data.clientCard   
                this.accounts = datos.data.accounts
                

            })

    },

    methods: {
        signOut(){
            axios.post('/api/logout').then(response =>{ 
            window.location.href = "/web/index.html"
        })
        }


    },



}).mount('#app')