document.addEventListener("DOMContentLoaded", async () => {
  [...document.querySelectorAll(".add-basket")].forEach((el) => {
    el.addEventListener("click", addToCart);
  });
  document.querySelector(".cart-btn").addEventListener("click", modalBuilder);
});

const modalBuilder = async () => {
  const json = await fetchBasket();
  document.querySelector(".modal-section").innerHTML = paymentModal(json);
  new bootstrap.Modal(document.querySelector("#pay-modal")).show();
  bodyListener();
};

const bodyListener = () => {
  document
    .querySelector(".btn-pay")
    .addEventListener("click", async (event) => {
      const res = await fetch("/store/pay", {
        method: "POST",
      });
      if (res.ok) {
        document.querySelector(
          ".modal-body"
        ).innerHTML = `<p class=h2 text-center text-succes>Thank You!
      Your Order is on the way</p>`;
        document.querySelector(".close-modal").addEventListener("click", () => {
          window.location.href = window.location.href;
        });
      } else modalBuilder();
    });

  [...document.querySelectorAll("button.delete-book")].forEach((el) => {
    el.addEventListener("click", async (event) => {
      await fetch(event.currentTarget.getAttribute("href"), {
        method: "POST",
      });
      const json = await fetchBasket();
      document.querySelector(".modal-body").innerHTML = modalBody(json);
      bodyListener();
    });
  });
};

const fetchBasket = async () => {
  const res = await fetch("/store/getbasket");
  const json = await res.json();
  return json;
};

const toastMsg = (msg, err = false) => {
  return `<div
  class="toast toast-cart align-items-center text-bg-primary border-0"
  role="alert"
  aria-live="assertive"
  aria-atomic="true"
><div class="d-flex">
  <div class="toast-body">${msg}</div>
  <button
    type="button"
    class="btn-close btn-close-white me-2 m-auto"
    data-bs-dismiss="toast"
    aria-label="Close"
  ></button>
</div>
</div>`;
};

const addToCart = async (event) => {
  event.preventDefault();
  const res = await fetch(event.currentTarget.getAttribute("href"), {
    method: "POST",
    credentials: "include",
  });
  const json = await res.json();
  if (res.ok) {
    document.querySelector(".toastMsg").innerHTML = toastMsg(
      "Succesfully added to cart"
    );
  } else {
    document.querySelector(".toastMsg").innerHTML = toastMsg(json, true);
  }
  new bootstrap.Toast(document.querySelector(".toast-cart")).show();
};

const deleteFromCart = async () => {
  const res = await fetch("/store/deletebasket/0", {
    method: "POST",
    credentials: "include",
  });
  const json = await res.json();
  console.log(json);
};

const paymentModal = (orders) => {
  return `<div
  id="pay-modal"
  class="modal fade"
  data-bs-backdrop="static"
  data-bs-keyboard="false"
  tabindex="-1"
  aria-labelledby="staticBackdropLabel"
  aria-hidden="true"
>
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-primary">
        <h5
          class="modal-title text-white"
          id="staticBackdropLabel"
        >Cart</h5>
        <button
          type="button"
          class="btn-close close-modal"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>
      <div class="modal-body">
        <div class="row">
          ${modalBody(orders)}
        </div>
      </div>
    </div>
  </div>
</div>
`;
};

const modalBody = (orders) => {
  if (orders === null || orders.length === 0) {
    return `<div class="h2 text-center">Cart is Empty</div>
    <hr />
    <div class="col-auto mx-auto text-center my-3">
        <button
          type="button"
          class="btn btn-primary"
          data-bs-dismiss="modal"
        >
          Continue Shopping
        </button>
    </div>`;
  }
  let totalPrice = 0;
  let totalQuantity = 0;
  const body = orders.map((el) => {
    totalPrice += parseFloat(el.price);
    totalQuantity += parseInt(el.quantity);
    return createTableRow(el);
  });

  return `<div class="col">
            <table class="table table-border">
              <thead>
                <th>Name</th>
                <th>quantity</th>
                <th>Price</th>
                <th></th>
              </thead>
              <tbody>
                ${body.toString()}
              </tbody>
            </table>
          </div>
          <div class="col-12 mx-auto fw-bold text-center my-3">Total Price: ${totalPrice.toFixed(
            2
          )} $</div>
          <div class="col-auto mx-auto text-center my-3">

              <button type="submit" class="btn btn-primary btn-pay">
                <strong>Pay</strong>
              </button>
              <button
                type="button"
                class="btn btn-primary"
                data-bs-dismiss="modal"
              >
                Continue Shopping
              </button>
          </div>`;
};

const createTableRow = (order) => {
  return `<tr>
  <td>${order.name}</td>
  <td>${order.quantity}</td>
  <td>${order.price} $</td>
  <td>
  <button
    type="button"
    class="btn btn-transparent delete-book"
    href="/store/deletebasket/${order.id}"
  >
    <img src="/resources/asserts/delete.png" alt="delete" />
  </button>
</td>`;
};
