Vue.createApp({
            data() {
                return {
                    nombre: "",
                    accounts: [],
                    amount: "",
                    description: "",
                    cuentaP: "",
                    cuentaD: "",
                    accountType:"myCuenta",
                    myCuenta:"",
                    otraCuenta:"",


                }
            },

            created() {
                axios.get("/api/clients/current")
                    .then(datos => {
                        this.accounts = datos.data.accounts
                        this.nombre = datos.data.firstName

                    })

            },

            methods: {
                signOut() {
                    axios.post('/api/logout').then(response => {
                        // console.log('signed out!!!')
                        window.location.href = "/web/index.html"
                    })
                },
                sendTransfer() {

                    Swal.fire({
                            title: "Are you sure?",
                            text: "who wants to make a transfer?",
                            icon: "warning",
                            showCancelButton: true,
                            confirmButtonColor: '#014377',
                            cancelButtonColor: '#ff0000',
                            confirmButtonText: 'Transfer now'
                        })

                        .then((result) => {
                            if (result.isConfirmed) {

                                axios.post('/api/transactions', `amount=${this.amount}&description=${this.description}&originAccountNumber=${this.cuentaP}&destinationAccountNumber=${this.cuentaD}`, {
                                        headers: {
                                            'content-type': 'application/x-www-form-urlencoded'
                                        }
                                    })
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
                                        Swal.fire({
                                            icon: 'error',
                                            text: error.response.data,

                                        })
                                    })


                            }


                        });
                    },







                    //   .then(response =>  window.location.href = "/web/accounts.html")
                    // axios.post('/api/transactions', `amount=${this.amount}&description=${this.description}&originAccountNumber=${this.cuentaP}&destinationAccountNumber=${this.cuentaD}`, {
                    //         headers: {
                    //             'content-type': 'application/x-www-form-urlencoded'
                    //         }
                    //     })

                    ///api/transactions?amount=300&description=para el postre&originAccountNumber=VIN003&destinationAccountNumber=VIN002



                }
                }).mount('#app')