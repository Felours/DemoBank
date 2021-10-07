let utilities = (function(){

    let result = {};

    result.addEvent = function(selector, event, logic){

        let elements = document.querySelectorAll(selector);

        if(elements){

            elements.forEach(function(element){

                element.addEventListener(event, logic);

            });

        }

    };

    return result;

})();