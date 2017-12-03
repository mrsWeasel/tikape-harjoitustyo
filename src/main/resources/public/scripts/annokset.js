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
            
            function elementFactory(obj) {
                
                var element = document.createElement(obj.tagname);
                if (obj.hasOwnProperty("type")) {
                    element.setAttribute("type", obj.type);
                }
                if (obj.hasOwnProperty("name")) {
                    element.setAttribute("name", obj.name);
                }
                if (obj.hasOwnProperty("value")) {
                    element.setAttribute("value", obj.value);
                }
                if (obj.hasOwnProperty("classname") && obj.classname !== "") {
                    element.setAttribute("class", obj.classname);
                }
                if (obj.hasOwnProperty("content") && obj.content !== "") {
                    element.innerText = obj.content;
                }
                return element;
            }
            
            function Element(tagname, type, name, value, classname, content) {
                this.tagname = tagname;
                this.type = type;
                this.name = name;        
                this.value = value;
                this.classname = classname;
                this.content = content;
            }
            
            
            
            button.addEventListener("click", function(e) {
               e.stopPropagation();
               e.preventDefault();
               
               // eka valinta ei ole raaka-aine vaan "otsikko"
               if (select.value < 0) {
                   return;
               }
               if (e.target.nodeName === "BUTTON") {
                   var div = document.createElement("div");
                   
                   var inputNimi = elementFactory(new Element("input", "hidden", "ra_nimi_" + select.value, select.value, "", ""));
                   var inputNimiLabel = elementFactory(new Element("span", "", "", "", "label label-default", select.options[select.selectedIndex].text));
                   var inputMaara = elementFactory(new Element("input", "number", "ra_maara_" + select.value, maaraInput.value, "", ""));
                   var inputYksikko = elementFactory(new Element("input", "hidden", "ra_yksikko_" + select.value, yksikkoInput.value, "", ""));
                   var inputYksikkoLabel = elementFactory(new Element("span", "", "", "", "", yksikkoInput.value));
                   var poista = elementFactory(new Element("button", "", "", "", "poista btn btn-danger btn-xs", "Poista"));
                   poista.dataset.ra = select.value;
                   //var inputMaara = document.createElement("input");
                   //inputMaara.setAttribute("type", "number");
                   //inputMaara.setAttribute("name", "ramaara_" + select.value);
                   //inputMaara.setAttribute("value", maaraInput.value);
                   
                   //var inputYksikko = document.createElement("input");
                   //inputYksikko.setAttribute("type", "hidden");
                   //inputYksikko.setAttribute("name", "rayksikko_" + select.value);
                   //inputYksikko.setAttribute("value", yksikkoInput.value);
                   
                   //var inputYksikkoLabel = document.createElement("span");
                   //inputYksikkoLabel.innerText = yksikkoInput.value;
                   
                   //var poista = document.createElement("button");
                   //poista.setAttribute("class", "poista btn btn-danger btn-xs");
                   //poista.dataset.ra = select.value;
                   //poista.innerText = "Poista";
                   
                   lisatyt.appendChild(div);
                   div.appendChild(inputNimiLabel);
                   div.appendChild(inputMaara);
                   div.appendChild(inputYksikkoLabel);
                   div.appendChild(poista);
                   div.appendChild(inputNimi);
                   div.appendChild(inputYksikko);
 
               }
              
            });
            
