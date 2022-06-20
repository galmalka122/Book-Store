const contentElement = document.querySelector("#content");
const fetchURL = new URL(`${window.location.origin}/bookapi`);
fetchURL.searchParams.append("size", 10);
fetchURL.searchParams.append("page", 1);
const content = await (await fetch(fetchURL)).json();

const { num, tot, last, first } = function createCard(books) {
  return `<div class="row row-cols-2 row-cols-lg-5 g-4">
    ${books.array.map((book) => {
      cardCreator(book);
    })}
  </div>`;
};

function cardCreator(book) {
  return `<div class="col">
    <div class="card text-center h-100">
    <div class="ratio">
      <img src="${book.image}" alt="cover" class="card-img-top img-fluid"/>
      </div>
      <div class="card-body">
        <div class="h5 user-select-none card-title">${book.name}</div>
        <p class="card-text">
            <h3 class="text-warning">${book.price} &#36</h3>
        </p>
        <a ${
          book.quantity == 0 ? " disabled" : ""
        } href="#" class="btn btn-primary">
          <span>${
            book.quantity == 0 ? "Currently unavailable" : "Add to basket"
          } </span>
        </a>
      </div>
    </div>
  </div>`;
}
