function showForm(formId) {
    document.getElementById(formId).classList.remove("hidden");
}

function hideForm(formId) {
    document.getElementById(formId).classList.add("hidden");
}


function loadProducts() {

    fetch("addProduct")
        .then(response => response.json())
        .then(products => {

            const table = document.getElementById("productTable");

            table.innerHTML = "";

            if (products.length === 0) {

                table.innerHTML = `
                    <tr>
                        <td colspan="7">
                            No products found.
                        </td>
                    </tr>
                `;

                return;
            }

            products.forEach(product => {

                const row = `
                    <tr>

                        <td>${product.id}</td>

                        <td>${product.name}</td>

                        <td>${product.sku}</td>
                        <td>${product.category}</td>

                        <td>₹${product.price}</td>

                        <td>${product.quantity}</td>

                        <td>
                            <button type="button" onclick="editProduct(${product.id})">
                                Edit
                            </button>

                            <button type="button" onclick="deleteProduct(${product.id})">
                                Delete
                            </button>
                        </td>

                    </tr>
                `;

                table.innerHTML += row;

            });

        })
        .catch(error => {

            console.error("Error loading products:", error);

            document.getElementById("productTable").innerHTML = `
                <tr>
                    <td colspan="7">
                        Error loading products.
                    </td>
                </tr>
            `;
        });
}
function loadOrders() {

    fetch("addOrder")
        .then(response => response.json())
        .then(orders => {

            const table = document.getElementById("orderTable");

            table.innerHTML = "";

            if (orders.length === 0) {

                table.innerHTML = `
                    <tr>
                        <td colspan="5">
                            No orders found.
                        </td>
                    </tr>
                `;

                return;
            }

            orders.forEach(order => {

                const row = `
                    <tr>

                        <td>${order.id}</td>

                        <td>Customer #${order.customerId}</td>

                        <td>₹${order.amount}</td>

                        <td>${order.status}</td>

                        <td>
                            <button type="button">
                                View
                            </button>
                        </td>

                    </tr>
                `;

                table.innerHTML += row;

            });

        })
        .catch(error => {

            console.error("Error loading orders:", error);

            document.getElementById("orderTable").innerHTML = `
                <tr>
                    <td colspan="5">
                        Error loading orders.
                    </td>
                </tr>
            `;
        });
}


function loadCustomers() {

    fetch("addCustomer")
        .then(response => response.json())
        .then(customers => {

            const table =
                document.getElementById("customerTable");

            table.innerHTML = "";

            if (customers.length === 0) {

                table.innerHTML = `
                    <tr>
                        <td colspan="6">
                            No customers found.
                        </td>
                    </tr>
                `;

                return;
            }

            customers.forEach(customer => {

                const row = `
                    <tr>

                        <td>${customer.id}</td>

                        <td>${customer.name}</td>

                        <td>${customer.email}</td>

                        <td>${customer.phone}</td>

                        <td>${customer.address}</td>

                        <td>
                            <button type="button">
                                View
                            </button>
                        </td>

                    </tr>
                `;

                table.innerHTML += row;

            });

        })
        .catch(error => {

            console.error(
                "Error loading customers:",
                error
            );

            document.getElementById("customerTable").innerHTML = `
                <tr>
                    <td colspan="6">
                        Error loading customers.
                    </td>
                </tr>
            `;
        });
}

function loadCustomerDropdown() {

    fetch("addCustomer")
        .then(response => response.json())
        .then(customers => {

            const dropdown =
                document.getElementById("orderCustomer");

            if (!dropdown) {
                return;
            }

            customers.forEach(customer => {

                const option =
                    document.createElement("option");

                option.value = customer.id;
                option.textContent = customer.name;

                dropdown.appendChild(option);
            });

        })
        .catch(error => {

            console.error(
                "Error loading customer dropdown:",
                error
            );

        });
}



