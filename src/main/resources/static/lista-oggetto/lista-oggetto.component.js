'use strict';

// Register `listaOggetto` component, along with its associated controller and template
angular.
module('listaOggetto').
component('listaOggetto', {
    templateUrl: 'lista-oggetto/lista-oggetto.template.html',
    controller: ['$http', '$location', function listaOggettoController($http, $location) {

        var self = this;

        self.getAll = function () {
            $http.get('oggetto/').then(function(response) {
                self.oggetti = response.data;
                console.log("Success: " + response.statusText);
            }, function (reason) {
                console.log("Error: " + reason.statusText);
            });
        };

        self.getAll();

        self.dettagli = function (oggettoId) {
            $location.path('/dettagli-oggetto/' + oggettoId.toString());
        };

    }]
});