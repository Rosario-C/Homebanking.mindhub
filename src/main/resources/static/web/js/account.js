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
        axios.get("/api/clients/current")
            .then(datos => {
                this.nombre = datos.data.firstName
                this.accounts = datos.data.accounts
                this.loans = datos.data.loans
            })


        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('id');

        axios.get(`/api/accounts/+${myParam}`)
            .then(datos => {

                this.transactions = datos.data.transactions

                this.sotedTransactions = this.transactions.sort((a, b) => b.id - a.id)

            })


    },

    methods: {
        signOut() {
            axios.post('/api/logout').then(response => {
               
                window.location.href = "/web/index.html"
            })
        }
    }





}).mount('#app')