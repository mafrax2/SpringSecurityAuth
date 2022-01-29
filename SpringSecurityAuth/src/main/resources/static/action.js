
function onSelectType() {

    var e = document.getElementById("transacType");
    var type = e.options[e.selectedIndex].text;
var button = document.getElementById("submitTransactionId");
     var userselect = document.getElementById("forInput");
    if(type=="BANK"){
         alert("Bank transactions are to make a deposit from or to your bank account, set a negative amount to receive money from your bank account");
         userselect.disabled= true;

    } else {
         userselect.disabled= false;
    }
}

function onAmountInput(balance){

            var e = document.getElementById("transacType");
            var type = e.options[e.selectedIndex].text;
            var button = document.getElementById("submitTransactionId");
            var amount = document.getElementById("amount");

            var fee = amount.value*0.0005;
            var allowed = Number(amount.value) + Number(fee);


            if (type=="USER" && amount.value<0){
             alert("No negative transaction allowed between users");
                button.disabled=true;
            } else if(isNaN(Number(amount.value)) && amount.value !== "-") {
            alert("Enter a number");
                button.disabled=true;
            } else if (allowed> balance){
            alert("Insufficient funds, max amount allowed :" + allowed);
            button.disabled=true;
            } else {
                button.disabled=false;
            }



}