var storedCars = [];
var filteredCars = [];

document.addEventListener("DOMContentLoaded", e => {
   fetch("api/car/all")
       .then(res => res.json())
       .then(data =>  storedCars = data)
       .then(data => makeTable(data));
});

const objectKeys = ["id","year","make","model","price"];

function makeTable(cars) {
    var thead = document.querySelector("#tableHead");
    var headerData = `<tr>`;
    for(var keyIndex = 0; keyIndex < objectKeys.length; keyIndex++) {
        headerData += `
        <th>
        <button id="${objectKeys[keyIndex]}">${objectKeys[keyIndex]}</button>
        </th>`;
    }
    headerData += `</tr>`;
    thead.innerHTML = headerData;
    thead.addEventListener("click", e => {
        sort(e.target.id);
    })
    populateTable(cars);
}

function populateTable(cars) {
    const tableData = cars.map(car =>`<tr> <td>${car.id}</td> <td>${car.year}</td> <td>${car.make}</td> <td>${car.model}</td> <td>${car.price}</td> </tr>`);
    document.querySelector("#tableBody").innerHTML = tableData.join("");
}

function filter() {
    filteredCars = storedCars;
    filteredCars = filteredCars.filter( car => {
        return (filterYear(car) && filterMake(car) && filterModel && filterPrice(car));
    });
    populateTable(filteredCars);
}

function clearFilter() {
    populateTable(storedCars);
}

function filterYear(car) {
    var value = document.querySelector("#filterYear").value;
    if(Number(value) === 0) return true;
    else return Number(value) === Number(car.year);
}

function filterMake(car) {
    var value = document.querySelector("#filterMake").value;
    if(value === "") return true;
    else return value.toLowerCase() === car.make.toLowerCase();
}

function filterModel(car) {
    var value = document.querySelector("#filterModel").value;
    if(value === "") return true;
    else return value.toLowerCase() === car.model.toLowerCase();
}

function filterPrice(car) {
    var value = document.querySelector("#filterPrice").value;
    if(Number(value) === 0) return true;
    else return Number(value) === Number(car.price);
}

function sort(id) {
    if(id === "tableHead") {
        // The user didn't click on a specific button.
    } else {
        var copy;
        if(filteredCars.length === 0)
            copy = storedCars;
        else
            copy = filteredCars;
        if(id === "id") {
            copy.sort( (car1,car2) => car1.id > car2.id ? 1 : -1);
        } else if (id === "make") {
            copy.sort((car1,car2) => car1.make.toUpperCase() > car2.make.toUpperCase() ? 1 : -1);
        } else if (id === "model") {
            copy.sort((car1,car2) => car1.model.toUpperCase() > car2.model.toUpperCase() ? 1 : -1);
        } else if (id === "price") {
            copy.sort((car1,car2) => car1.price > car2.price ? 1 : -1);
        } else if (id === "year") {
            copy.sort((car1,car2) => car1.year > car2.year ? 1 : -1);
        }
    }
    populateTable(copy);
}