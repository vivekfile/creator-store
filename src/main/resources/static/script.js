const productsContainer = document.getElementById("products");

fetch("/api/products")
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to fetch products");
        }

        return response.json();
    })
    .then(products => {

        productsContainer.innerHTML = "";

        products.forEach(product => {

            const card = document.createElement("div");

            card.classList.add("product-card");

            card.innerHTML = `
                <h2>${product.name}</h2>

                <p>
                    <strong>Description:</strong>
                    ${product.description ?? "No description available"}
                </p>

                <p>
                    <strong>Category:</strong>
                    ${product.Category ?? "Not specified"}
                </p>

                <p>
                    <strong>Price:</strong>
                    ₹${product.price}
                </p>

                <p>
                    <strong>Stock:</strong>
                    ${product.stockQuantity}
                </p>
            `;

            productsContainer.appendChild(card);
        });
    })
    .catch(error => {
        console.error(error);

        productsContainer.innerHTML =
            "<p>Unable to load products.</p>";
    });
