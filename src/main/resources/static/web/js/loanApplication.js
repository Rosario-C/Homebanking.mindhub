Vue.createApp({
    data() {
        return {
            loanTypeId:1,
            borrowerAccount:"",
            amount:0,
            nombre: "",
            accounts: [],
            payments:"",
            loans:{},


        }
    },

    created() {
        axios.get("http://localhost:8080/api/clients/current")
        .then(datos => {
            this.accounts = datos.data.accounts
        }),
        axios.get("/api/loans")
        .then(datos => {
            this.loans= datos.data
            this.loans = this.loans.sort((a, b) => a.id - b.id)
            console.log(this.loans)

        })

    },

    methods: {

        signOut() {
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href = "/web/index.html"
            })
        },
        applyLoan(){
            Swal.fire({
                title: "Are you sure?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: '#014377',
                cancelButtonColor: '#ff0000',
                confirmButtonText: 'Apply Loan'
            })

            .then((result) => {
                if (result.isConfirmed) {
                
                    axios.post('/api/loans',{loanId:this.loanTypeId,borrowerAccount:this.borrowerAccount,amount:this.amount,payments:this.payments} //
                        // headers: {
                        //     'content-type': 'application/JSON'
                        // }
                    ).then(response => console.log("Loan Approved"))

                        .then(() => {
                            Swal.fire({
                                title: 'Exitos!',
                                text: "Your transaction was created successfully!",
                                icon: "success",
                                timer: 2000

                            }).then(() => location.reload())
                            .then(response =>  window.location.href = "/web/accounts.html")

                        })

                        .catch((error) => {
                            console.log(error.response.data)
                            Swal.fire({
                                icon: 'error',
                                text: error.response.data,

                            })
                        })


                }


            });

        },
        selecPayments(){
            let aux = [...this.loans]
            aux = aux.filter((loan) => this.loanTypeId == loan.id);
            return aux[0].payments
    
    },
    


    },

}).mount('#app')

