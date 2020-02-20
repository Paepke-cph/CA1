async function getJokes() {
    let response = await fetch("api/joke/all");
    let data = await response.json();
    return data;
}

async function getRandomJoke() {
    let response = await fetch("api/joke/random");
    let joke = await response.json();
    return joke;
}

async function getSpecificJoke(id) {
    let response = await fetch("api/joke/" + id);
    if(response.status === 200) {
        let joke = await response.json();
        return joke;
    } else {
        return null;
    }
}

function makeTable() {
    var table = document.getElementById("jokeTable");
    if(table !== null) {
        table.remove();
    }
    table = document.createElement("TABLE");
    var tableHeaders = ["Joke", "Reference", "Type"];
    var container = document.getElementById("header");
    table.setAttribute("id", "jokeTable");
    var initialRow = document.createElement("TR");
    tableHeaders.forEach(header => {
        var element = document.createElement("TH");
        element.innerText = header;
        initialRow.append(element);
    });
    table.append(initialRow);
    getJokes()
        .then( jokes => {
            for(var jokeIndex = 0; jokeIndex < jokes.length; jokeIndex++) {
                var row = document.createElement("TR");
                var joke = jokes[jokeIndex];
                var c1 = document.createElement("TH");
                c1.innerText = joke.text;
                row.append(c1);
                var c2 = document.createElement("TH");
                c2.innerText = joke.reference;
                row.append(c2);
                var c3 = document.createElement("TH");
                c3.innerText = joke.type;
                row.append(c3);
                table.append(row);
            }}
        );
    container.append(table);
}

function updateWithRandom() {
    var para = document.getElementById("jokeField");
    getRandomJoke().then(joke => {
        para. innerText = joke.text
    });
}

function updateWithSpecific() {
    var para = document.getElementById("jokeField");
    var input = document.getElementById("jokeID");
    getSpecificJoke(input.value).then(joke => {
        if(joke == null) {
            para.innerText = "Couldnote"
        } else {
            para.innerText = "Could not find that Joke!";
            para.innerText = joke.text
        }
    });
}

function makeButtonGrid() {
    var input = document.createElement("INPUT");
    input.setAttribute("type", "number");
    input.setAttribute("id", "jokeID");
    var specific = document.createElement("BUTTON");
    var random = document.createElement("BUTTON");
    var headerBottom = document.getElementById("headerBottom");
    specific.innerText = "Get Specific";
    specific.addEventListener("click", updateWithSpecific);
    random.innerText = "Get Random";
    random.addEventListener("click", updateWithRandom);

    var paragraph = document.createElement("P");
    paragraph.setAttribute("id", "jokeField");

    headerBottom.append(input);
    headerBottom.append(specific);
    headerBottom.append(random);
    headerBottom.append(paragraph);
}

(function(){
    makeTable();
    makeButtonGrid();
})();