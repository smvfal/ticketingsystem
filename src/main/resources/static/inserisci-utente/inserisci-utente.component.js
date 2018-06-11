'use strict';

// Register `inserisciUtente` component, along with its associated controller and template
angular.
module('inserisciUtente').
component('inserisciUtente', {
    templateUrl: 'inserisci-utente/inserisci-utente.template.html',
    controller: ['$http', '$location', function inserisciUtenteController($http, $location) {

        var self = this;

        self.getRuoli = function (){
            $http.get('ruolo/').then(function (response) {
                self.ruoli = response.data;
                alert(self.ruoli);
            }, function (reason) {
                alert("Error: " + reason.status);
            });
        };

        self.getRuoli();

        self.inserisci = function () {
            //console.log(self.utente);

            $http.post('utente/', self.utente)
                .then(function (response) {
                    $location.path('/utente');
                    alert("utente inserito con successo!");
                }, function (reason) {
                    alert('Error: ' + JSON.stringify(reason));
            });
        };

        self.annulla = function () {
            $location.path('/utente');
        };
    }]
});