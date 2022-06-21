Vue.createApp({
    data() {
        return {
            nombre: "",
            transactions: [],
            accounts: [],
            sortedTransactions: [],
            loans:[],
            



        }
    },
    created() {
        axios.get("http://localhost:8080/api/clients/current")
            .then(datos => {
                this.nombre = datos.data.firstName
                this.accounts = datos.data.accounts
                this.loans = datos.data.loans
            })


        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('id');

        axios.get(`http://localhost:8080/api/accounts/+${myParam}`)
            .then(datos => {

                this.transactions = datos.data.transactions

                this.sotedTransactions = this.transactions.sort((a, b) => b.id - a.id)

            })


    },

    methods: {
        signOut() {
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href = "/web/index.html"
            })
        }
    }





}).mount('#app')