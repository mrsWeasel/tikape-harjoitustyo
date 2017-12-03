            var url = new URL(window.location.href);
            var notification = document.getElementById("notification");
            var error = url.searchParams.get("virhe");
            
            // jos samanniminen annos on jo olemassa, tallentaminen ei onnistu
            if (error) {
                notification.innerText = "Nimi on jo käytössä.";
            }
            
            var button = document.getElementById("add");
            var select = document.getElementById("select");
            var maaraInput = document.getElementById("maara");
            var yksikkoInput = document.getElementById("yksikko");
            var form = document.getElementsByTagName("form")[0];
            var lisatyt = document.getElementById("lisatyt");
            
            form.addEventListener("submit", function(e) {
                var inputs = lisatyt.getElementsByTagName("input");
                var counter = 0;
                // järjestysnumero ainesosille vasta lähetettäessä, 
                // koska järjestys voi muuttua ainesosien lisäämisen jälkeen
                for (var i = 0; i < inputs.length; i++) {
                    // järjestysnumero tarvitaan vain yhteen kenttään / rivi
                    if (inputs[i].hasAttribute("name") && inputs[i].getAttribute("name").includes("ra_nimi_")) {
                        var n = inputs[i].getAttribute("name");
                        inputs[i].setAttribute("name", n + "[" + counter + "]");
                        counter ++;
                    }
                    
                }
            });
            
            function elementFactory(obj) {
                
                var element = document.createElement(obj.tagname);
                if (obj.hasOwnProperty("type") && obj.type !== "") {
                    element.setAttribute("type", obj.type);
                }
                if (obj.hasOwnProperty("name")&& obj.name !== "") {
                    element.setAttribute("name", obj.name);
                }
                if (obj.hasOwnProperty("value") && obj.value !== "") {
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
            
            function disableOption(value) {
                var options = select.getElementsByTagName("option");
                for (var i = 0; i < options.length; i++) {
                    if (options[i].value === value && options[i].disabled == false) {
                        options[i].disabled = true;
                    } 
                }
            }
            // ok, ei ole kauniisti tehty toistoineen kaikkineen, mutta kiire :)
            function undisableOption(value) {
                var options = select.getElementsByTagName("option");
                for (var i = 0; i < options.length; i++) {
                    if (options[i].value === value && options[i].disabled == true) {
                        console.log("Jihuuuu");
                        options[i].disabled = false;
                    } 
                }
            }
            
            lisatyt.addEventListener("click", function(e) {
               e.stopPropagation();
               e.preventDefault();
               if (e.target.nodeName === "BUTTON") {
               var that = e.target;
               // div    
               var parent = that.parentElement;
               
               var id = that.dataset.ra;
               console.log(id);
               // jos raaka-aine poistetaan, vapautetaan se uudelleen käyttöön valikossa
               undisableOption(id);
               
               parent.parentElement.removeChild(parent);
               
               }
            });
            
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
                   
                   // lisää kaikki elementit formin perään
                   lisatyt.appendChild(div);
                   div.appendChild(inputNimiLabel);
                   div.appendChild(inputMaara);
                   div.appendChild(inputYksikkoLabel);
                   div.appendChild(poista);
                   div.appendChild(inputNimi);
                   div.appendChild(inputYksikko);
                   
                   // jo valittu raaka-aine pois käytöstä ja valikon nollaus
                   disableOption(select.value);
                   select.value = -1;
                   maaraInput.value = 1;
                   yksikkoInput.value = "kpl";
               }
              
            });
            
