Vue.createApp({
    data() {
        return {
            cardType:"",
            cardColor:"",


        }
    },

    created() {
        axios.get("/api/clients/current")
        .then(datos =>{
            this.accounts = datos.data.accounts
            this.nombre = datos.data.firstName
        } )





    },

    methods: {
        newCard(){
            axios.post('/api/clients/current/cards', `type=${this.cardType}&color=${this.cardColor}`).then(response => {
                
                window.location.href = "/web/cards.html"
            })
    },
   
    
    
    


    },

}).mount('#app')