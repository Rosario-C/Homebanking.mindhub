Vue.createApp({
    data() {
        return {
            cardType:"",
            cardColor:"",


        }
    },

    created() {
        axios.get("http://localhost:8080/api/clients/current")
        .then(datos =>{
            this.accounts = datos.data.accounts
            this.nombre = datos.data.firstName
        } )





    },

    methods: {
        newCard(){
            axios.post('/api/clients/current/cards', `type=${this.cardType}&color=${this.cardColor}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }).then(response => {
                
                window.location.href = "/web/cards.html"
            })
    },
   
    
    
    


    },

}).mount('#app')