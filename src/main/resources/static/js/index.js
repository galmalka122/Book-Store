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

/**
 * creates cards to view store books
 */
document.addEventListener("DOMContentLoaded", async () => {
  const booksDiv = document.querySelector("#books");
  const res = await fetch("/books-api/");
  const json = [...(await res.json())];
  json.forEach((element) => {
    booksDiv.innerHTML += cardCreator(element);
  });
});


