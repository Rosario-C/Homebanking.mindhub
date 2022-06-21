
Vue.createApp({
    data() {
        return {
            message: 'Hello Vue!',
            Json: [],
            clients: [],
            nombre: "",
            apellido: "",
            mail: "",
        }
    },

    created() {
        axios.get("http://localhost:8080/rest/clients")
            .then(datos => {

                this.clients = datos.data._embedded.clients;
                this.Json = datos.data;
            })

    },

    methods: {
        postClients() {
            axios.post("http://localhost:8080/rest/clients", {
                    firstName: this.nombre,
                    lastName: this.apellido,
                    email: this.mail,
                })
                .then(response => {
                    console.log(response)
                })
                .catch(error => {
                    console.log(error)

                })


        },

        deleteClient(client) {

            axios.delete(client._links.client.href)
                .then(location.reload())
        },

        editClient(client) {
            Swal.fire({
                title: 'Edit',
                html: `<input id="swal-input1" class="swal2-input" value="${client.firstName}">` +
                    `<input id="swal-input2" class="swal2-input" value = "${client.lastName}">` +
                    `<input id="swal-input3" class="swal2-input" value = "${client.email}">`,
                focusConfirm: false,
                preConfirm: () => {
                    let cambioNombre = document.getElementById('swal-input1').value;
                    let cambioApellido = document.getElementById('swal-input2').value;
                    let cambioEmail = document.getElementById('swal-input3').value;

                    let editedClient = {
                        firstName: cambioNombre,
                        lastName: cambioApellido,
                        email: cambioEmail,

                    };
                    axios.put(client._links.client.href, editedClient)
                    .then(location.reload())
                }
            })
        }
    }

}).mount('#app')