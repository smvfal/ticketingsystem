'use strict';

angular.
  module('ticketingSystemApp').
  config(['$locationProvider' ,'$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/', {
          template: ''
        }).
        when('/utente', {
          template: '<lista-utente></lista-utente>'
        }).
        when('/ticket', {
          template: '<lista-ticket></lista-ticket>'
        }).
        when('/nuovo-ticket', {
          template: '<inserisci-ticket></inserisci-ticket>'
        }).
        when('/nuovo-utente', {
          template: '<inserisci-utente></inserisci-utente>'
        }).
        when('/utente/:utenteId?', {
          template: '<dettagli-utente></dettagli-utente>'
        }).
        when('/ticket/:ticketId?', {
          template: '<dettagli-ticket></dettagli-ticket>'
        }).
        otherwise('/');
    }
  ]);