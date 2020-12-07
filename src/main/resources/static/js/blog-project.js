(function() {
    "use strict";

    $(document).ready(function() {

        function setAttributes(el, attrs) {
            for(var key in attrs) {
                el.setAttribute(key, attrs[key]);
            }
        }

        var counter = 1;
        var limit = 3;
        function addInput(formImages){
            if (counter === limit)  {
                alert("You have reached the limit of adding 3 images");
            }
            else {
                let newInput = document.createElement('input');
                setAttributes(newInput, {placeholder:"Image Path", name:"image-input" + counter, class:"form-control mb-1", type:"text"})
                document.getElementById('formImages').appendChild(newInput);
                counter++;
            }
        }


        $('#addMoreImages').on('click', function() {
            addInput()
        });



    });

})();