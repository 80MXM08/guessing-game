function closeFormAndOpenForm(formId1, formId2) {
		closeForm(formId1);
		openForm(formId2);
	}

function openForm(formId) {
  document.getElementById(formId).style.display = "block";
}

function closeForm(formId) {
  document.getElementById(formId).style.display = "none";
}

function moveToNextInput(currentInput) {
    const value = currentInput.value.replace(/[^0-9]/gi, "");
    currentInput.value = value;
    const maxLength = currentInput.maxLength;
    const elementName = currentInput.name;
    const match = elementName.match(/\[(\d+)\]/);
    if (match) {
        const number = parseInt(match[1]);
        if (value.length >= maxLength) {
            const nextInput = document.getElementsByName(`guess[${number + 1}]`)[0];
            if (nextInput) {
                nextInput.focus();
            } else {
                document.getElementById(`btn_guess`).focus();
            }
        }
    }
}