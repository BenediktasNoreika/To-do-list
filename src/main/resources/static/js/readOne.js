const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}

var listId = sessionStorage.getItem("ListId");


function getData(id){
    fetch('http://localhost:9092/task/read/'+id)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
          // Examine the text in the response
          response.json().then(function(data) {
             console.log("MY DATA OBJ",data)

             document.querySelector("input#listId").value = listId
             document.querySelector("input#Id").value = data.id
             document.querySelector("input#name").value = data.name
             document.querySelector("input#dueDate").value = data.dueDate
             document.querySelector("select#completed").value = data.completed
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }


    document.querySelector("form.viewRecord").addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements)
    
    let listId = formElements["listId"].value;
    let id = formElements["Id"].value;
    let name=formElements["name"].value;
    let dueDate =formElements["dueDate"].value;
    let completed =formElements["completed"].value;
    console.log(completed);

    let data = {
      
      "name":name,
      "dueDate":dueDate,
      "completed":completed,
      "taskList":{
        "id":listId
    }

      
      
    }
    console.log("Data to post",data)
    console.log(id)

    sendData(data,id)
    // postData(noteTitle,noteBody)
  });


  function sendData(data,id){
    fetch("http://localhost:9092/task/update/"+id, {
        method: 'put',
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        },
        body:JSON.stringify(data)
      })
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
    }


    function deleteByid(id){
      fetch("http://localhost:9092/car/delete/"+id, {
          method: 'delete',
          headers: {
            "Content-type": "application/json; charset=UTF-8"
          },
        })
        
        
      }