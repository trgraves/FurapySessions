/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function checkField(event) {
    if (($("#petName")).val().length === 0) {
        alert("No name given. Please provide pet name.");
        return false;
    }
    return true;
}

function showResults() {
    var petResults = document.getElementById("petResults");
    if (petResults.style.display === "none") {
        petResults.style.display = "block";
        return false;
    } else {
        petResults.style.display = "none";
    }


}

function clearBreedAnalysis() {
    $('#petName').empty();
}



