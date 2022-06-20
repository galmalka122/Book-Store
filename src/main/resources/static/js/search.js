document.addEventListener("DOMContentLoaded", async () => {
  document.querySelector("#find-books").addEventListener("click", (event) => {
    event.preventDefault();
  });
  const res = await fetch("/books-api/");
  const json = [...(await res.json())];
  json.forEach((element) => {
    booksDiv.innerHTML += cardCreator(element);
  });
});
