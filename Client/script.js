var userId = null;
var state  = {};

var SERVER_ADDRESS = 'http://localhost:1337/';

var backgroundLoop = new Howl(
    {
        src: ['sound/background.ogg'],
        autoplay: false,
        loop: true,
        volume: 0.2,
        onend: function () {
        }
    }
);

var bgId;
new Howl(
    {
        src: ['sound/intro.ogg'],
        autoplay: true,
        loop: false,
        volume: 1,
        onend: function () {
            bgId = backgroundLoop.play();
            backgroundLoop.fade(0, 0.2, 1000, backgroundId);
        }
    }
);

var buttonSound = new Howl(
    {
        src: ['sound/button.ogg'],
        autoplay: false,
        loop: false,
        volume: 1,
        onend: function () {
        }
    }
);

var starSound = new Howl(
    {
        src: ['sound/star.ogg'],
        autoplay: false,
        loop: false,
        volume: 1,
        onend: function () {
        }
    }
);

var winSounds   = {};
var loseSounds  = {};
var themeSounds = {};

function addSound(name, key, dst) {
    dst[key] = new Howl(
        {
            src: [name],
            autoplay: false,
            loop: false,
            volume: 0.5
        }
    );
}
function addSounds(countryCode) {
    addSound('sound/' + countryCode + '/win.ogg', countryCode, winSounds);
    addSound('sound/' + countryCode + '/lose.ogg', countryCode, loseSounds);
    addSound('sound/' + countryCode + '/theme.ogg', countryCode, themeSounds);
}
addSounds('br');
addSounds('de');
addSounds('eg');
addSounds('fr');
addSounds('jp');
addSounds('oz');
addSounds('ru');
addSounds('us');
addSounds('it');

// var bgId = backgroundLoop.play();
function fadePlay(song) {
    backgroundLoop.fade(0.2, 0.05, 500, bgId);

    song.once('end', function () {
        backgroundLoop.fade(0.05, 0.2, 500, bgId);
    });

    setTimeout(function () {
        song.play();
    }, 1000);
}
function playSound(song) {
    song.play();
}

setTimeout(function () {
    $('#splash_screen').fadeOut(3000);
}, 2000);

function doRequest(requestData) {
    $.post(SERVER_ADDRESS, requestData, function (responseData) {
        if (requestData.action == 'login') {
            var s  = JSON.parse(responseData);
            userId = s.users[s.users.length - 1].id;
        }
    });
}

angular.module('ngViewExample', ['ngRoute', 'ngAnimate'])
       .config(['$routeProvider', '$locationProvider',
                function ($routeProvider, $locationProvider) {
                    $routeProvider
                        .when('/login', {
                            templateUrl: 'views/login.html',
                            controller: 'LoginCtrl',
                            controllerAs: 'login'
                        })
                        .when('/lobby', {
                            templateUrl: 'views/lobby.html',
                            controller: 'LobbyCtrl',
                            controllerAs: 'lobby'
                        })
                        .when('/world', {
                            templateUrl: 'views/world.html',
                            controller: 'WorldCtrl',
                            controllerAs: 'world'
                        })
                        .when('/question', {
                            templateUrl: 'views/question.html',
                            controller: 'QuestionCtrl',
                            controllerAs: 'question'
                        })
                        .when('/answerCheck', {
                            templateUrl: 'views/answercheck.html',
                            controller: 'AnswerCheckCtrl',
                            controllerAs: 'answercheck'
                        });

                    $locationProvider
                        .html5Mode(false)
                        .hashPrefix('!')
                }])
       .controller('MainCtrl', ['$scope', '$interval', '$route', '$routeParams', '$location', function MainCtrl($scope, $interval, $route, $routeParams, $location) {
           $scope.currentScore = 0;
           $scope.updateScore  = function () {
               var newScore = $scope.state.users[userId].points;

               if (!$scope.currentCountry) {
                   return;
               }

               if (newScore > $scope.currentScore) {
                   fadePlay(winSounds[$scope.currentCountry]);
               } else if (newScore == $scope.currentScore) {
                   fadePlay(loseSounds[$scope.currentCountry]);
               }

               $scope.currentScore = newScore;
           };

           $scope.updateCountry = function () {
               var newCountry = state.s.stateData.currentCountry;
               if (newCountry != $scope.currentCountry) {
                   fadePlay(themeSounds[newCountry]);
                   $scope.currentCountry = newCountry;
                   $scope.countryOverlay = newCountry;

                   setTimeout(function () {
                       $scope.$apply(function () {
                           $scope.countryOverlay = '';
                       })
                   }, 2500);
               }
           };

           $scope.getCountry = function (countryId) {
               var country;
               for (var i = 0; i < state.s.countries.length; i++) {
                   country = state.s.countries[i];
                   if (country.countryCode == countryId) {
                       break;
                   }
               }

               return country;
           };

           if (userId == null) {
               $location.path('/login');
           }

           $interval(function () {
               $.get(SERVER_ADDRESS, function (data) {
                   state.s = JSON.parse(data);
                   $scope.$apply(function () {
                       var newState = JSON.parse(data);

                       if ($scope.state && newState.state != $scope.state.state) {
                           if (newState.state == 'question') {
                               $scope.updateCountry();
                           }
                       }

                       $scope.state = newState;
                       if (userId != null) {
                           $location.path('/' + state.s.state);
                       }
                   });
               });
           }, 250);

           $scope.soundActive = true;
           $scope.toggleSound = function () {
               playSound(buttonSound);
               $scope.soundActive = !$scope.soundActive;
               if ($scope.soundActive) {
                   backgroundLoop.play();
               } else {
                   backgroundLoop.stop();
               }
           }

       }])
       .controller('LoginCtrl', ['$scope', '$location', '$routeParams', function LoginCtrl($scope, $location, $routeParams) {
           $scope.name     = '';
           $scope.joinGame = function () {
               playSound(buttonSound);
               if ($scope.name) {
                   doRequest({'action': 'login', 'name': $scope.name});
                   $location.path('/lobby');
               }
           }
       }])
       .controller('LobbyCtrl', ['$scope', '$location', '$routeParams', function LobbyCtrl($scope, $location, $routeParams) {
           $scope.state     = state;
           $scope.startGame = function () {
               if ($scope.isGameMaster()) {
                   doRequest({'action': 'startgame'});
               }
           };

           $scope.isGameMaster = function () {
               return userId == 0;
           };
       }])
       .controller('WorldCtrl', ['$scope', '$location', '$routeParams', function WorldCtrl($scope, $location, $routeParams) {
           $scope.state = state;
           $scope.updateScore();
       }])
       .controller('QuestionCtrl', ['$scope', '$location', '$routeParams', function QuestionCtrl($scope, $location, $routeParams) {
           $scope.answer   = '';
           $scope.answered = false;
           $scope.state    = state;

           $scope.getCurrentCountry = function () {
               if (!state.s || !state.s.countries) {
                   return '';
               }

               return $scope.getCountry(state.s.stateData.currentCountry).name;
           };

           $scope.isUserDone = function (userId) {
               console.log(state.s);

               if (state.s && state.s.stateData && state.s.stateData.finishedUsers) {
                   return state.s.stateData.finishedUsers.indexOf(userId) >= 0;
               }
               return false;
           };

           $scope.sendAnswer = function (answer) {
               playSound(buttonSound);
               doRequest({'action': 'answer', 'user_id': userId, 'answer': answer});
               $scope.answered = true;
           };

           $scope.getTimeoutProgress = function () {
               if (!state.s.stateData || !state.s.stateData.questionTimeout) {
                   return 100;
               }

               var remainingTime = state.s.stateData.questionTimeout - (Date.now() / 1000);
               return Math.round(remainingTime / 30 * 100);
           };
       }])
       .controller('AnswerCheckCtrl', ['$scope', '$location', '$routeParams', function AnswerCheckCtrl($scope, $location, $routeParams) {
           $scope.state = state;

           $scope.hoverIndex     = -1;
           $scope.hoverLevel     = -1;
           $scope.mouseOver      = function (index, level) {
               $scope.hoverIndex = index;
               $scope.hoverLevel = level;
           };
           $scope.mouseOut       = function () {
               $scope.hoverIndex = -1;
               $scope.hoverLevel = -1;
           };
           $scope.showFilledStar = function (userId, level) {
               if ($scope.hoverIndex == userId && $scope.isGameMaster()) {
                   return $scope.hoverLevel >= level;
               }

               if (state.s.stateData && state.s.stateData.answerStates.hasOwnProperty(userId)) {
                   return state.s.stateData.answerStates[userId] >= level;
               }

               return false;
           };
           $scope.getPoints      = function (userId) {
               var points = 0;
               if (state.s.stateData && state.s.stateData.answerStates.hasOwnProperty(userId)) {
                   points = state.s.stateData.answerStates[userId];
                   points *= state.s.stateData.repeatMultiplier;
               }
               return '+' + points + ' P.';
           };

           $scope.sendState = function (userId, level) {
               if ($scope.isGameMaster()) {
                   playSound(starSound);
                   doRequest({'action': 'answercheck', 'user_id': userId, 'answer_state': level});
               }
           };

           $scope.submitCheck = function () {
               playSound(buttonSound);
               doRequest({'action': 'answerchecksubmit'});
           };

           $scope.isGameMaster = function () {
               return userId == 0;
           };
       }]);
