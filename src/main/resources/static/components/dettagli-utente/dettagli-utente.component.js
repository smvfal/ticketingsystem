'use strict';

// Register `dettagliUtente` component, along with its associated controller and template
angular.
module('dettagliUtente').
component('dettagliUtente', {
    templateUrl: 'dettagli-utente/dettagli-utente.template.html',
    controller: ['$http', '$routeParams', '$location', function dettagliUtenteController($http, $routeParams, $location) {

        var self = this;
        var modifyMode = false;

        self.get = function () {
            $http.get('utente/' + $routeParams.utenteId.toString()).
            then(function(response) {
                self.utente = response.data;
            }, function (reason) {
                alert(reason.toLocaleString());
            });
        };

        self.get();

        self.indietro = function () {
            $location.path('/utente');
        };

        self.elimina = function (utenteId) {

            if (confirm("Procedere con l'eliminazione?")) {
                $http.delete('utente/' + utenteId.toString()).then(function () {
                    $location.path('/utente');
                    alert("Utente eliminato con successo!");
                }, function (reason) {
                    alert(reason.toLocaleString());
                });
            }
        };

        self.modifica = function () {
            self.modifyMode = true;
        };

        self.annulla = function () {
            self.modifyMode = false;
        };

        self.conferma = function () {
            $http.put('utente/' + self.utente.id.toString(), self.utente).
            then(function () {
                alert("Utente modificato con successo!");
                self.modifyMode = false;
            }, function (reason) {
                alert(reason.toLocaleString());
            });
        };

    }]
});