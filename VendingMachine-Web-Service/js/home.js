$(document).ready(function () {

    var money = 0.00;


    clearButtons();
    loadItems();

    $('#itemId').hide();

    $('#add-dollar').click(function (event) {
        money += 1.00;
        $('#money-inserted').val(money);
    });
    $('#add-dime').click(function (event) {
        money += 0.10;
        $('#money-inserted').val(money);
    });
    $('#add-quarter').click(function (event) {
        money += 0.25;
        $('#money-inserted').val(money);
    });
    $('#add-nickel').click(function (event) {
        money += 0.05;
        $('#money-inserted').val(money);
    });

    $('#make-purchase').click(function (event) {
        var itemId = $('#itemId').val();
        vendItem(money, itemId);
    });

    $('#return-change').click(function (event) {
        money = 0.00;
        $('#money-inserted').val(money);
        displayMoney(money);
        $('#messages').val(" ");
        $('#item-index').val(" ");
        $('#itemId').val(" ");
        $('#change').val(" ");
        clearButtons();
        loadItems();
    });

})

function loadItems() {


    $.ajax({
        method: "GET",
        url: "http://tsg-vending.herokuapp.com/items",
        success: function(allItems) {
            $.each(allItems, function(index, item){
                var actualIndex = index + 1;
                var itemId = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;

                //
                var box = '<div class="col-md-4 button text-center">';
                    box += '<button onclick="displayItemIndex(' + actualIndex + '); storeItemId(' + itemId + ')" type="button" class="btn btn-default">';
                    //box += '<p id="item' + itemId + '"></p>'
                    box += '<p class="text-left">' + actualIndex + '</p>';
                    box += '<p>' + name + '<br/>';
                    box += '$' + price + '<br/>' + '<br/>';
                    box += 'Quantity Left: ' + quantity + '</p>';
                    box += '</button>';
                    box += '</div>';

                $('#buttonDiv').append(box);

            });

        },
        error: function() {
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service.  Please try again later.'));
        }

    })

}


function vendItem(money, itemId) {

    $.ajax({
        method: "POST",
        url: 'http://tsg-vending.herokuapp.com/money/' + money + '/item/' + itemId,
        'dataType': 'json',
        success: function (data, status) {
            var quarters = data.quarters;
            var dimes = data.dimes;
            var nickels = data.nickels;
            var pennies = data.pennies;
            var changeLine = 'Quarters: ' + quarters + ' dimes: ' + dimes + ' nickels: ' + nickels + ' pennies: ' + pennies;

            $('#change').val(changeLine);
            $('#messages').val("Thank you!");



        },
        error: function(jqXHR, status, thrownError) {
            $('#messages').val(jqXHR.responseJSON.message);
        }

    })

}

function displayItemIndex(actualIndex) {
    $('#item-index').val(actualIndex);

}

function storeItemId(itemId) {
    $('#itemId').val(itemId);
}

function displayMoney(money) {
    $('#show-money-inserted').val(money);
}

function clearButtons() {
    $('#buttonDiv').empty();
}