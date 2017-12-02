            var url = new URL(window.location.href);
            var notification = document.getElementById("notification");
            var error = url.searchParams.get("virhe");
            
            if (error) {
                notification.innerText = "Nimi on jo käytössä.";
            }
            
            var button = document.getElementById("add");
            var select = document.getElementById("select");
            var maaraInput = document.getElementById("maara");
            var yksikkoInput = document.getElementById("yksikko");
            var form = document.getElementsByTagName("form")[0];
            var lisatyt = document.getElementById("lisatyt");
            console.log(button);
            
            button.addEventListener("click", function(e) {
               e.stopPropagation();
               e.preventDefault();
               if (e.target.nodeName == "BUTTON") {
                   var div = document.createElement("div");
                   
                   var inputNimi = document.createElement("input");
                   inputNimi.setAttribute("type", "hidden");
                   inputNimi.setAttribute("name", "ranimi_" + select.value);
                   inputNimi.setAttribute("value", select.value);
                   
                   var inputNimiLabel = document.createElement("span");
                   inputNimiLabel.innerText = select.options[select.selectedIndex].text;
                   
                   var inputMaara = document.createElement("input");
                   inputMaara.setAttribute("type", "number");
                   inputMaara.setAttribute("name", "ramaara_" + select.value);
                   inputMaara.setAttribute("value", maaraInput.value);
                   
                   var inputYksikko = document.createElement("input");
                   inputYksikko.setAttribute("type", "hidden");
                   inputYksikko.setAttribute("name", "rayksikko_" + select.value);
                   inputYksikko.setAttribute("value", yksikkoInput.value);
                   
                   var inputYksikkoLabel = document.createElement("span");
                   inputYksikkoLabel.innerText = yksikkoInput.value;
                   
                   var poista = document.createElement("button");
                   poista.setAttribute("class", "poista btn btn-danger btn-xs");
                   poista.dataset.ra = select.value;
                   poista.innerText = "Poista";
                   
                   lisatyt.appendChild(div);
                   div.appendChild(inputNimiLabel);
                   div.appendChild(inputMaara);
                   div.appendChild(inputYksikkoLabel);
                   div.appendChild(poista);
                   div.appendChild(inputNimi);
                   div.appendChild(inputYksikko);
 
               }
              
            });
            
