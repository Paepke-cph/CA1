async function getMembers() {
    let response = await fetch("api/groupmembers/all")
    let data = await response.json();
    return data;
}

function makeTable() {
    var table = document.getElementById("memberTable");
    if(table !== null) {
        table.remove();
    }
    table = document.createElement("TABLE");
    var tableHeaders = ["Name", "Student ID", "Color"];
    var container = document.getElementById("header");
    table.setAttribute("id", "memberTable");
    var initialRow = document.createElement("TR");
    tableHeaders.forEach(header => {
        var element = document.createElement("TH");
        element.innerText = header;
        initialRow.append(element);
    });
    table.append(initialRow);
    getMembers()
        .then( members => {
                for(var memberIndex = 0; memberIndex < members.length; memberIndex++) {
                    var row = document.createElement("TR");
                    var member = members[memberIndex];
                    var c1 = document.createElement("TH");
                    c1.innerText = member.name;
                    row.append(c1);
                    var c2 = document.createElement("TH");
                    c2.innerText = member.studentId;
                    row.append(c2);
                    var c3 = document.createElement("TH");
                    c3.innerText = member.colorLevel;
                    row.append(c3);
                    table.append(row);
                }}
        );
    container.append(table);
}

function makeReloadButton() {
    var button = document.createElement("BUTTON");
    button.setAttribute("id", "reloadButton");
    button.innerText = "Reload Members";

    button.addEventListener("click", e => {
        makeTable();
    });

    var container = document.getElementById("headerBottom");
    container.append(button);
}

(function(){
    makeTable();
    makeReloadButton();
})();