import { edit } from "./edit.js";

/**
 * add listeners to the admins operation forms to view as modal
 * @param className - the requested operation as classname
 * @param modalID - the requested operation as modal id
 * @param op - the requested operations
 */
function addListeners(className, modalID, op) {
  [...document.getElementsByClassName(className)].forEach((el) => {
    el.addEventListener("click", async (event) => {
      const id = event.target.closest(`button.${className}`).dataset.bookId;
      const res = await fetch(
        `${window.location.origin}/admin/${op}${id ? "/" + id : ""}`,
        {
          method: "GET",
          credentials: "include",
        }
      );
      if (res.redirected) window.location.replace(res.url);
      const html = await res.text();
      document.getElementById("modal-section").innerHTML = html;
      const myModal = new bootstrap.Modal(document.getElementById(modalID));
      myModal.show();
      edit.main();
    });
  });
}

/**
 * adds listeners tothe forms
 */
document.addEventListener("DOMContentLoaded", async () => {
  addListeners("update-book", "update-modal", "update");
  addListeners("delete-book", "delete-modal", "delete");
  addListeners("add-book", "add-modal", "addbook");
  const toast = document.querySelector("div.toast");
  if (toast !== null) {
    new bootstrap.Toast(toast).show();
  }
});
