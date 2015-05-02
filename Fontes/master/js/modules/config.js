/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/

App.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', 'RouteHelpersProvider',
function ($stateProvider, $locationProvider, $urlRouterProvider, helper) {
  'use strict';

  // Set the following to true to enable the HTML5 Mode
  // You may have to set <base> tag in index and a routing configuration in your server
  $locationProvider.html5Mode(false);

  // defaults to dashboard
  $urlRouterProvider.otherwise('/app/process-list');

  // 
  // Application Routes
  // -----------------------------------   
  $stateProvider
    .state('app', {
        url: '/app',
        abstract: true,
        templateUrl: helper.basepath('app.html'),
        controller: 'AppController',
        resolve: helper.resolveFor('fastclick', 'modernizr', 'icons', 'screenfull', 'animo', 'sparklines', 'slimscroll', 'classyloader', 'toaster', 'whirl')
    })
	
	/*Custom provides*/
	 .state('app.process-list', {
        url: '/process-list',
        title: 'Process List',
        templateUrl: helper.basepath('process-list.html'),
		controller:"process-list"
	 })	 
	 .state('app.process-add', {
        url: '/process-add',
        title: 'Process Add',
        templateUrl: helper.basepath('process-add.html'),
		controller: "process-add",
		resolve: helper.resolveFor('ngDialog')
    })	
	 .state('app.process-detail', {
        url: '/process-detail/:id',
        title: 'Process Detail',
        templateUrl: helper.basepath('process-detail.html'),
		controller:"process-detail",
		resolve: angular.extend(helper.resolveFor('ngDialog'),{
          tpl: function() { return { path: helper.basepath('ngdialog-template.html') }; }
        })
    })	
	.state('app.form-upload', {
        url: '/form-upload',
        title: 'Form upload',
        templateUrl: helper.basepath('form-upload.html'),
        resolve: helper.resolveFor('angularFileUpload', 'filestyle')
    })		
	
	.state('app.workflow-list', {
        url: '/workflow-list',
        title: 'Fluxo de Trabalho',
        templateUrl: helper.basepath('workflow-list.html'),
		controller: "workflow-list"
    })	
	.state('app.workflow-add', {
        url: '/workflow-add/:id',
        title: 'Fluxo de Trabalho',
        templateUrl: helper.basepath('workflow-add.html'),
		resolve: helper.resolveFor('htmlSortable','ngDialog','xeditable','ui.select'),//'codemirror', 'moment', 'taginput','inputmask','localytics.directives', 'ui.bootstrap-slider', 'ngWig', 'filestyle'
		controller: "workflow-add"
    })
	 .state('app.users', {
        url: '/users',
        title: 'Users',
        templateUrl: helper.basepath('users.html')
    })
	
	
	
	/*Custom provides*/
    .state('app.dashboard', {
        url: '/dashboard',
        title: 'Dashboard',
        templateUrl: helper.basepath('dashboard.html'),
        resolve: helper.resolveFor('flot-chart','flot-chart-plugins')
    })
   
    .state('app.widgets', {
        url: '/widgets',
        title: 'Widgets',
        templateUrl: helper.basepath('widgets.html'),
        resolve: helper.resolveFor('loadGoogleMapsJS', function() { return loadGoogleMaps(); }, 'google-map')
    })
   
    
    // 
    // CUSTOM RESOLVES
    //   Add your own resolves properties
    //   following this object extend
    //   method
    // ----------------------------------- 
    // .state('app.someroute', {
    //   url: '/some_url',
    //   templateUrl: 'path_to_template.html',
    //   controller: 'someController',
    //   resolve: angular.extend(
    //     helper.resolveFor(), {
    //     // YOUR RESOLVES GO HERE
    //     }
    //   )
    // })
    ;


}]).config(['$ocLazyLoadProvider', 'APP_REQUIRES', function ($ocLazyLoadProvider, APP_REQUIRES) {
    'use strict';

    // Lazy Load modules configuration
    $ocLazyLoadProvider.config({
      debug: false,
      events: true,
      modules: APP_REQUIRES.modules
    });

}]).config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
    function ( $controllerProvider, $compileProvider, $filterProvider, $provide) {
      'use strict';
      // registering components after bootstrap
      App.controller = $controllerProvider.register;
      App.directive  = $compileProvider.directive;
      App.filter     = $filterProvider.register;
      App.factory    = $provide.factory;
      App.service    = $provide.service;
      App.constant   = $provide.constant;
      App.value      = $provide.value;

}]).config(['$translateProvider', function ($translateProvider) {

    $translateProvider.useStaticFilesLoader({
        prefix : 'app/i18n/',
        suffix : '.json'
    });
    $translateProvider.preferredLanguage('pt_BR');
    $translateProvider.useLocalStorage();
    $translateProvider.usePostCompiling(true);

}]).config(['tmhDynamicLocaleProvider', function (tmhDynamicLocaleProvider) {

    tmhDynamicLocaleProvider.localeLocationPattern('vendor/angular-i18n/angular-locale_{{locale}}.js');

    // tmhDynamicLocaleProvider.useStorage('$cookieStore');

}]).config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {

    cfpLoadingBarProvider.includeBar = true;
    cfpLoadingBarProvider.includeSpinner = false;
    cfpLoadingBarProvider.latencyThreshold = 500;
    cfpLoadingBarProvider.parentSelector = '.wrapper > section';

}]).config(['$tooltipProvider', function ($tooltipProvider) {

    $tooltipProvider.options({appendToBody: true});

}])
;
