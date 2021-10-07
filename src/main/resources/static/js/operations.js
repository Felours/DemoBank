let operations = (function(){

    const IDS = {

        OPERATIONS: {

            VIREMENT: "virement",
            CODE_COMPTE_VIREMENT: "codeCompteVirement"

        },

        CODE_COMPTE_VIREMENT_BLOC: "codeCompteVirementBloc"

    };

    const CSS = {

        HIDE: "hide",
        OPERATIONS: {
            OPERATION_TYPE: "operationType"
        }

    };

    let result = {

        init: function(){

            _initEvents();

        },

    };

    function _initEvents(){

        _initTypeOperationEvent();

    };

    function _initTypeOperationEvent(){

        utilities.addEvent("." + CSS.OPERATIONS.OPERATION_TYPE, "change", _changeOperationVirementEvent);

    };

    function _changeOperationVirementEvent(e){

        let target = e.target;
        let isVirement = target.getAttribute('id') === IDS.OPERATIONS.VIREMENT;

        let isChecked = target.checked;
        let codeCompteVirement = document.getElementById(IDS.OPERATIONS.CODE_COMPTE_VIREMENT);
        let codeCompteVirementBloc = document.getElementById(IDS.CODE_COMPTE_VIREMENT_BLOC);

        if(isChecked){

            if(isVirement) {

                codeCompteVirementBloc.classList.remove(CSS.HIDE);
                codeCompteVirement.removeAttribute("disable");

            }
            else{

                codeCompteVirementBloc.classList.add(CSS.HIDE);
                codeCompteVirement.setAttribute("disable", "disable");

            }

        }
        else{

            if(isVirement) {

                codeCompteVirementBloc.classList.add(CSS.HIDE);
                codeCompteVirement.removeAttribute("disable");

            }

        }

    };

    return result;

})();