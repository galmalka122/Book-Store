export const edit = (function () {
  const errorHtml = function (error) {
    return `<div class="alert alert-danger text-center py-0 m-0 mt-2 form-error" role="alert" >
        ${error}
    </div>`;
  };

  /**
   * trims all form values
   * @param formElement - the form element
   */
  function trimFormValues(formElement) {
    formElement.forEach((element) => {
      element.value = element.value.trim();
    });
  }

  function clearErrs() {
    document.querySelectorAll(".form-error").forEach((el) => {
      el.remove();
    });
  }

  /**
   * checks for empty input
   * @param input - user's input
   * @returns {{isValid: boolean, message: string}} - validation state and message
   */
  const isEmpty = async (input) => {
    return {
      isValid: input.value !== undefined && input.value !== "",
      message: `${input.name} is required`,
    };
  };

  /**
   * checks if input is a float number
   * @param input - user's input
   * @returns {{isValid: boolean, message: string}} - validation state and message
   */
  const isFloatNumber = async (input) => {
    return {
      isValid: !isNaN(parseFloat(+input.value)),
      message: `${input.name} must be a decimal number: only digits, a single minus and a single dot ar allowed`,
    };
  };

  /**
   * checks if input is a float number
   * @param input - user's input
   * @returns {{isValid: boolean, message: string}} - validation state and message
   */
  const isImageURL = async (input) => {
    try {
      const res = await fetch(input.value);
      if (!res.headers.get("content-type").includes("image")) {
        input.value = "";
      }
      return { isValid: true, message: "" };
    } catch (e) {
      if (!/\.(jpg|jpeg|png|webp|avif|gif|svg)$/.test(input.value))
        input.value = "";
      return { isValid: true, message: "" };
    }
  };

  const isPositiveNumber = async (input) => {
    return {
      isValid: +input.value >= 0,
      message: `${input.name} must be a positive number`,
    };
  };

  const isGreaterThanZero = async (input) => {
    return {
      isValid: +input.value > 0,
      message: `${input.name} must be a greater than zero`,
    };
  };
  /** creates an error message if input isn't valid and returns the validation state
   * @param inputElement - the input value
   * @param validateFunc - the function for validation
   * @returns {boolean|*} - the final validation state
   **/
  const validateInput = async (inputElement, validateFunc) => {
    let v = await validateFunc(inputElement); // call the validation function
    const errMsg = errorHtml(v.message);
    if (!v.isValid) {
      inputElement.parentElement.innerHTML += errMsg;
    }
    return v.isValid;
  };
  /**
   * runs on each query found and validates by functions sent
   * @param nodeAndValidatorArray - a query:function object to perform validations
   * @param checkAll - indicates if validation should stop for first failure
   * @returns {Promise<boolean>} - a boolean to inform if all inputs are valid
   */
  const validateForm = async (nodeAndValidatorArray, checkAll = true) => {
    let v = true; //for node check
    for (const [query, functions] of Object.entries(nodeAndValidatorArray)) {
      let vf = true; //for each function check
      for (const func of functions) {
        vf = vf && (await validateInput(document.querySelector(query), func));
        if (!vf) break;
      }
      v = v && vf;
      if (!!v && !checkAll) {
        break;
      }
    }
    return !!v;
  };

  const main = () => {
    document
      .querySelector('button[type="submit"]')
      .addEventListener("click", async (event) => {
        event.preventDefault();
        clearErrs();

        //the password and confirmation password elements
        const inputs = document.querySelectorAll("input.form-control");
        if (inputs.length == 0) {
          document.getElementById("edit-form").submit();
        }

        trimFormValues(inputs);

        //the element + validators
        let validations = {
          "input[name='name']": [isEmpty],
          "input[name='price']": [isEmpty, isFloatNumber, isGreaterThanZero],
          "input[name='discount']": [isEmpty, isFloatNumber, isPositiveNumber],
          "input[name='quantity']": [isEmpty, isFloatNumber, isGreaterThanZero],
          "input[name='image']": [isImageURL],
        };

        //validate form input
        if (await validateForm(validations))
          document.getElementById("edit-form").submit();
      });
  };
  return {
    main: main,
  };
})();
