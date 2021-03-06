
function createElement(data){

    for (const q of data) {
  
            const myDiv = document.querySelector('#myDiv');
  
            const pid = document.createElement('p');
            const pname = document.createElement('p');
            const pmake = document.createElement('p');
            const pmodel = document.createElement('p');
            const button = document.createElement('a')
            const div = document.createElement('div');
            const newLine = document.createElement('br');
  
  
          myDiv.className = 'alert alert-danger';          //for styling
          pid.innerHTML ="ID:"+q.id
          pname.innerHTML ="Task name is:"+q.name
          pmake.innerHTML =q.dueDate
          pmodel.innerHTML =q.completed
          button.innerHTML = "View"
          button.className = "btn btn-danger"
          button.href = "readOne.html?id="+q.id
        div.append(pid, pname, pmake, pmodel, button, newLine);
        myDiv.append(div);
    }
  }
  
  
  fetch('http://localhost:9092/taskList/read/')
    .then(
      function(response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
  
        
        response.json().then(function(commentData) {
  
          console.log(commentData[0])
        
         
           let table = document.querySelector("table");
           let data = Object.keys(commentData[0]);
      
           createTableHead(table);
          createTableBody(table,commentData);
          
        });
      }
    )
    .catch(function(err) {
      console.log('Fetch Error :-S', err);
    });
  
    function createTableHead(table){
        let tableHead= table.createTHead();
        let row = tableHead.insertRow();
       
            let th = document.createElement("th");
            let text = document.createTextNode("Task List name");
            th.appendChild(text);
            row.appendChild(th)
          
        }
   
    function createTableBody(table,commentData){
        for(let commentRecord of commentData){
            let row = table.insertRow();
            
                let cell = row.insertCell();
                let text = document.createTextNode(commentRecord.name);
                console.log(commentRecord);
                cell.appendChild(text);
              
              let newCell = row.insertCell();
              let myViewButton = document.createElement("a");
              let myButtonValue = document.createTextNode("View/add")
              myViewButton.className ="btn btn-warning pull-right";
              myViewButton.href="readAll.html?id="+commentRecord.id;
              myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
              let newCellDelete = row.insertCell();
              let myDelButton = document.createElement("button");
              let myButtonValue1 = document.createTextNode("Delete List")
               myDelButton.className ="btn btn-danger pull-right";
               myDelButton.onclick = function(){
               
                    fetch("http://localhost:9092/taskList/delete/"+commentRecord.id, {
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
  
  
