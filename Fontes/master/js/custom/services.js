// To run this code, edit file 
// index.html or index.jade and change
// html data-ng-app attribute from
// angle to myAppName
// ----------------------------------- 

mexGED.service('services', function($log, $rootScope, $http, $q, toaster) {
	var factory = {};
	var userInfo;
	var currentProcess;
	var currentFlow;
	
	
	factory.getUserInfo = function () {
		return userInfo;		
	};	
	
	factory.getCurrentProcess = function () {
		return currentProcess;		
	};	
	
	factory.setCurrentFlow = function (data) {
		currentFlow = data;		
	};	
	
	factory.getCurrentFlow = function () {
		return currentFlow;		
	};	
	
	factory.setCurrentProcess = function (data) {
		currentProcess = data;		
	};
	
	factory.setError = function(erro){
		alert(erro.msg);
	}
	
	factory.getProcessList = function () {	
		var deferred = $q.defer();
		$http.get("server/list-process.json")
		.success(function(data){
			deferred.resolve(data);
			}).error(function(error){
			factory.setError({error:error,msg:"Tivemos um problema ao obter os dados do monitoramento, verifique a sua conexão e tente novamente"});
		});
		
		return deferred.promise;
	};		
	
	factory.getProcessDetail = function () {	
		var deferred = $q.defer();
		$http.get("server/detail-process.json")
		.success(function(data){
			deferred.resolve(data);
			}).error(function(error){
			factory.setError({error:error,msg:"Tivemos um problema ao obter os dados do monitoramento, verifique a sua conexão e tente novamente"});
		});
		
		return deferred.promise;
	};	
	
	factory.getWorkflowList = function () {	
		var deferred = $q.defer();
		$http.get("server/list-workflow.json")
		.success(function(data){
			deferred.resolve(data);
			}).error(function(error){
			factory.setError({error:error,msg:"Tivemos um problema ao obter os dados do monitoramento, verifique a sua conexão e tente novamente"});
		});
		
		return deferred.promise;
	};		
	
	factory.getWorkflowDetail = function () {	
		var deferred = $q.defer();
		$http.get("server/detail-workflow.json")
		.success(function(data){
			deferred.resolve(data);
			}).error(function(error){
			factory.setError({error:error,msg:"Tivemos um problema ao obter os dados do monitoramento, verifique a sua conexão e tente novamente"});
		});
		
		return deferred.promise;
	};	
	
	return factory;
	
});
