'use strict';

// Register `listaTicket` component, along with its associated controller and template
angular.
module('listaTicket').
component('listaTicket', {
    templateUrl: 'components/lista-ticket/lista-ticket.html',
    controller: ['$http', '$location','$scope', function listaTicketController($http, $location,$scope) {

        var self = this;

        $scope.sortType     = 'id'; // set the default sort type
        $scope.sortReverse  = false;  // set the default sort order
        $scope.searchFilter   = '';


        self.getAll = function () {
            $http.get('ticket/').then(function(response) {
                self.tickets = response.data;
            }, function (reason) {
                alert("Error: " + JSON.stringify(reason));
            });
        };

        self.getAll();

        self.dettagli = function (ticketId) {
            $location.path('/dettagli-ticket/' + ticketId.toString());
        };

        self.elimina = function (ticketId) {
            $http.delete('ticket/' + ticketId.toString()).
            then(function () {
                self.getAll();
                alert("Ticket eliminato con successo!");
            }, function (reason) {
                alert("Error: " + reason.statusText);
            })
        };

    }]
});
