"use strict";
var API = 'http://mailfy.net:8181/cxf/mailfy/';

angular
  .module('mailfy', [
    'ui.router',
    'ngAnimate'
  ])
  .config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.when('/dashboard', '/dashboard/index');
    $urlRouterProvider.otherwise('/login');

    $stateProvider
      .state('base', {
        abstract: true,
        url: '',
        templateUrl: 'views/base.html'
      })
        .state("login", {
            url: "/login",
            parent: "base",
            templateUrl: "views/login.html",
            controller: "LoginController"
        }).state("dashboard", {
            url: "/dashboard",
            parent: "base",
            templateUrl: "views/dashboard.html",
            controller: "DashboardController"
        }).state("index", {
            url: "/index",
            parent: "dashboard",
            templateUrl: "views/dashboard/index.html"
        }).state("emailalerts", {
            url: "/emailalerts",
            parent: "dashboard",
            templateUrl: "views/dashboard/emailalerts.html",
            controller: "DashboardController"
        }).state("addalert", {
            url: "/addalert",
            parent: "dashboard",
            templateUrl: "views/dashboard/addalert.html"
        });

  }), angular.module("mailfy").controller("LoginController", ["$scope", "$http", "$location", "$window", function($scope, $http, $location, $window) {
    if ($window.sessionStorage.token) {
        $location.path("/dashboard"), !1
    }
    $scope.submit = function() {
        $scope.credentials.dataLoading = true;
        $scope.credentials.showErrorMessage = false;
        delete $window.sessionStorage.token;

        $http({
          method: 'POST',
          url: API + 'auth/login',
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
          data: { 'email' : $scope.credentials.email,
                    'password': $scope.credentials.password }
        }).then(function successCallback(response) {
            $scope.credentials.dataLoading = false;
            $window.sessionStorage.token = response.data.token;
            $location.path("/dashboard"), !1
        
          }, function errorCallback(response) {
            $scope.credentials.password = '';
            $scope.credentials.showErrorMessage = true;
            $scope.credentials.dataLoading = false;
          });
    }
}]), angular.module("mailfy").controller("DashboardController", ["$scope", "$http", "$location", "$window", "$state", function($scope, $http, $location, $window, $state) {
    $scope.$state = $state;
    $scope.emailalert={};
    $scope.emailAlerts = [];
    var loginToken = $window.sessionStorage.token;
    if (!loginToken) {
        $location.path("/login"), !1
    }

    var getEmailAlerts = function () {
        $http({
          method: 'GET',
          url: API + 'emailalerts',
          params: {access_token: loginToken}
        }).then(function successCallback(response) {
            $scope.emailAlerts = response.data.emailAlert;
          }, function errorCallback(response) {
            delete $window.sessionStorage.token;
            $location.path("/login"), !1
        });
    };

    getEmailAlerts();

    $scope.createEmailAlert = function() {
        $scope.emailalert.showErrorMessage = false;
        $scope.emailalert.dataLoading = true;

        $http({
          method: 'POST',
          url: API + 'emailalerts?access_token=' + loginToken,
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
          data: { 'title' : $scope.emailalert.title,
                    'message': $scope.emailalert.message,
                    'target_date': $scope.emailalert.targetDate 
                }
        }).then(function successCallback(response) {
            $scope.emailalert.dataLoading = false;
            $location.path("/dashboard/emailalerts"), !1
          }, function errorCallback(response) {
            $scope.emailalert.dataLoading = false;
            $scope.emailalert.showErrorMessage = true;
          });
    }


    $scope.logout = function() {
        delete $window.sessionStorage.token;
        $http({
          method: 'GET',
          url: API + 'auth/logout/' + loginToken
        }).then(function successCallback(response) {
          }, function errorCallback(response) {
          });
        $location.path("/login"), !1
    }
}]);