const params = new URLSearchParams(window.location.search);
for(let param of params ){
  console.log("here i am",param)
  let id = param[1];
  console.log(id);
  getData(id)
  sessionStorage.setItem("ListId",id)
}  

  function getData(id){
  fetch('http://localhost:9092/task/findByList/'+id)
    .then(
      function(response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
  
        // Examine the text in the response
        response.json().then(function(commentData) {
  
          console.log(commentData[0])

           let table = document.querySelector("table");
           let data = Object.keys(commentData[0]); // first record in the array pos 0
          
           createTableHead(table,data);
          createTableBody(table,commentData);
          
        });
      }
    )
    .catch(function(err) {
      console.log('Fetch Error :-S', err);
    });
  }
  
    function createTableHead(table,data){
        let tableHead= table.createTHead();
        let row = tableHead.insertRow();
        for(let keys of data){
            // console.log("data",data)
            let th = document.createElement("th");
            let text = document.createTextNode(keys);
            th.appendChild(text);
            row.appendChild(th)
          }
        }
        // let th2 = document.createElement("th")
        // let text2 = document.createTextNode("View");
        // th2.appendChild(text2);
        // row.appendChild(th2);
        // let th3 = document.createElement("th")
        // let text3 = document.createTextNode("Delete");
        // th3.appendChild(text3);
        // row.appendChild(th3);
  
    function createTableBody(table,commentData){
        for(let commentRecord of commentData){
            let row = table.insertRow();
            for(let values in commentRecord){
                let cell = row.insertCell();
                let text = document.createTextNode(commentRecord[values]);
                cell.appendChild(text);
              }
              let newCell = row.insertCell();
              let myViewButton = document.createElement("a");
              let myButtonValue = document.createTextNode("Edit")
              myViewButton.className ="btn btn-warning pull-right";
              myViewButton.href="readOne.html?id="+commentRecord.id;
              myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
              let newCellDelete = row.insertCell();
              let myDelButton = document.createElement("button");
              let myButtonValue1 = document.createTextNode("Delete")
               myDelButton.className ="btn btn-danger pull-right";
               myDelButton.onclick = function(){
               
                    fetch("http://localhost:9092/task/delete/"+commentRecord.id, {
                        method: 'delete',
                        headers: {
                          "Content-type": "application/json; charset=UTF-8"
                        },
                      })
                      window.location.reload();
                      
                      
                    }
               
               myDelButton.appendChild(myButtonValue1);
               newCellDelete.appendChild(myDelButton)
         }
    }
  
  
