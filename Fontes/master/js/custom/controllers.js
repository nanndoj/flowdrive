// To run this code, edit file 
// index.html or index.jade and change
// html data-ng-app attribute from
// angle to myAppName
// ----------------------------------- 

var mexGED = angular.module('mexGED', ['angle']);

mexGED.run(function($log) {
	
	$log.log('I\'m a line from custom.js');
	
});

mexGED.config(function(RouteHelpersProvider) {
	// Custom Route definition
	
});

mexGED.controller('process-add', ['$scope', '$http', '$state','services', function($scope, $http, $state,services) {
	
	
}]);

mexGED.controller('file-add', ['$scope', '$http', '$state','services','$stateParams','$sce', function($scope, $http, $state,services,$stateParams,$sce) {
	$scope.newFile = false;
	$scope.newFileURL;
	
	services.getProcessDetail($stateParams.id).then(function(data){$scope.process = data;});
	
	$scope.createNewFile = function(type){ 
		if(type=='planilha'){
			$scope.newFileURL = "https://docs.google.com/a/rw3tecnologia.com/spreadsheets/d/1DrUdJmVoz0PXKXttf04P1jBf6pRkNUeHDRVnkptiqnY/edit#gid=0";
		}		
		if(type=='documento'){
			$scope.newFileURL = "https://docs.google.com/a/rw3tecnologia.com/document/d/1v8So6BOo344rI8jxM-E4XHuKxIsioNxIdEePkbw-Lls/edit";
		}
		$scope.newFile = true;
	}	
	
	$scope.saveNewFile = function(type){
		
		$scope.newFile = false;
	}
	
	$scope.trustSrc = function(src) {
		return $sce.trustAsResourceUrl(src);
	}
	
	
}]);

mexGED.controller('process-list', ['$scope', '$http', '$state','services', function($scope, $http, $state,services) {
	$scope.processList;
	$scope.viwer = 'card';
	
	services.getProcessList().then(function(data){$scope.processList = data;});
	
	$scope.openDetail = function(process) {
		//services.setCurrentProcess(process);
		$state.go("app.process-detail",{id: process.id});
	};
	
}]);

mexGED.controller('process-detail', ['$scope', '$http', '$state','services','ngDialog','$sce','$stateParams', function($scope, $http, $state,services,ngDialog,$sce,$stateParams) {
	$scope.newFile = false;
	$scope.newFileURL;
	$scope.edit = false;
	//$scope.process = services.getCurrentProcess();
	services.getProcessDetail($stateParams.id).then(function(data){$scope.process = data;});
	
	
	
}]);

mexGED.controller('process-upload', ['$scope', '$http', '$state','services','ngDialog', function($scope, $http, $state,services,ngDialog) {
	$scope.newFile = false;
	$scope.process = services.getCurrentProcess();
	
}]);
mexGED.controller('workflow-list', ['$scope','$state','services', function($scope,$state,services){
	/*$scope.flows = [{id:1, name:"Empenho de Nota",group:{"code":"2","description":"RH","color":"#FC2626"},steps:[{title:"Protocolo",description:"Protocolo do arquivo",users:[{name:"Diogo",email:"diogo@rw3tecnologia.com",url:"https://lh3.googleusercontent.com/-Zl3xznD8oVo/AAAAAAAAAAI/AAAAAAAAABM/0npNDp9ytYs/s120-c/photo.jpg",permission:"edit"},{name:"Daniel",email:"diogo@rw3tecnologia.com",url:"https://lh3.googleusercontent.com/-RBKy0gJ8gNs/AAAAAAAAAAI/AAAAAAAAAuw/tAtBvwZWxfE/s120-c/photo.jpg",permission:"viwer"},{name:"Diogo2",email:"diogo@rw3tecnologia.com",url:"https://lh3.googleusercontent.com/-Zl3xznD8oVo/AAAAAAAAAAI/AAAAAAAAABM/0npNDp9ytYs/s120-c/photo.jpg",permission:"edit"}]}]},
	{id:2, name:"Férias",group:{"code":"2","description":"RH","color":"#FC2626"}}];*/
	services.getWorkflowList().then(function(data){$scope.flows = data;});
	
	$scope.openDetail = function(flow){
		//services.setCurrentFlow(flow);
		$state.go("app.workflow-add",{id:flow.id});
	}
}]);
mexGED.controller('workflow-add', ['$scope','$stateParams','services','ngDialog','editableOptions','editableThemes','$filter', function($scope,$stateParams,services,ngDialog,editableOptions,editableThemes,$filter) {
	// Single List
	//$scope.flow = services.getCurrentFlow();
	$scope.flow = {};
	$scope.newUser = {};
	//Modo de inserção?
	if($stateParams.id!=0){
		services.getWorkflowDetail($stateParams.id).then(function(data){$scope.flow = data; $scope.flow.mode='A';});
		}else{
		$scope.flow.mode='I';
	}
	$scope.curretStep;
	
	$scope.person = {};
	$scope.people = [
    {id:1, name: 'Diogo Tedesco',      email: 'adam@email.com',      age: 10, url:"https://lh3.googleusercontent.com/-Zl3xznD8oVo/AAAAAAAAAAI/AAAAAAAAABM/0npNDp9ytYs/s120-c/photo.jpg" },
    {id:2, name: 'Daniel Piola',    email: 'amalie@email.com',    age: 12, url:"https://lh3.googleusercontent.com/-RBKy0gJ8gNs/AAAAAAAAAAI/AAAAAAAAAuw/tAtBvwZWxfE/s120-c/photo.jpg" },
    {id:3, name: 'Fernando Santos',  email: 'wladimir@email.com',  age: 30, url:"https://lh3.googleusercontent.com/-9rtQFmpIujE/AAAAAAAAAAI/AAAAAAAAACI/BYPVXdBlnf8/s120-c/photo.jpg" }
	];
	
	editableOptions.theme = 'bs3';
	
    editableThemes.bs3.inputClass = 'input-sm';
    editableThemes.bs3.buttonsClass = 'btn-sm';
    editableThemes.bs3.submitTpl = '<button type="submit" class="btn btn-success"><span class="fa fa-check"></span></button>';
    editableThemes.bs3.cancelTpl = '<button type="button" class="btn btn-default" ng-click="$form.$cancel()">'+
	'<span class="fa fa-times text-muted"></span>'+
	'</button>';
	$scope.permissions = [{value:'edit',text:'Editar'},{value:'viewer',text:'Visualizar'},{value:'comment',text:'Comentar'}];
	$scope.permissionsLabel = {edit:'Editar',viewer:'Visualizar',comment:'Comentar'};
	
	
	services.getWorkflowDetail().then(function(data){$scope.flows = data;});
	
	$scope.add = function (){
		alert("Salvo com sucesso - TODO: Enviar para o servidor");
		//TODO: Enviar para o serividor
	};
	
	$scope.sortableCallback = function (sourceModel, destModel, start, end) { 
		console.log(start + ' -> ' + end);
		console.log($scope.data1);
		//TODO: Salvar a lista depois de reodenar
		
	};
	
	$scope.openStep = function(step,index){
		step.index = index;
		if(step==0){step = {mode:'I'}}else{step.mode = "A"};
		$scope.curretStep = step;
		ngDialog.open({
			template: 'step-add',
			className: 'ngdialog-theme-default',
			scope: $scope 
		});
	}
	
	$scope.addStep = function(){
		$scope.curretStep
	}
	
	$scope.addState = function(newState){
		if($scope.flow.states){
			$scope.flow.states.push({id:1,name:newState});
			}else{
			$scope.flow.states = [{id:1,name:newState}];	
		}
		$scope.newState='';
	}
	
	$scope.removeState = function(index){
		$scope.flow.states.splice(index, 1);
	}
	
	$scope.saveStep = function(){
		if($scope.curretStep.mode=="I"){
			$scope.curretStep.id = 1;
			if($scope.flow.steps){
				$scope.flow.steps.push($scope.curretStep);
				}else{
				$scope.flow.steps = [$scope.curretStep];
				}
		}
		ngDialog.closeAll();
	}
	
	$scope.removeStep = function(step){
		if(confirm("Deseja remover esta etapa?")){
			$scope.flow.steps.splice(step.index, 1);
			ngDialog.closeAll();
		}
		}
		
	
	
	$scope.addUser = function(){	
		$scope.curretStep.newUser.permission = "edit";
		if($scope.curretStep.users){
			$scope.curretStep.users.push($scope.curretStep.newUser);
			$scope.curretStep.newUser = null;
			}else{
			$scope.curretStep.users = [$scope.curretStep.newUser];
			$scope.curretStep.newUser = null;
		}
	}
	
	$scope.removeUser = function(index){
		$scope.curretStep.users.splice(index, 1);
	}
	
	$scope.sortableOptions = {
		placeholder: '<div class="box-placeholder p0 m0"><div></div></div>',
		forcePlaceholderSize: true
	};
	
	
	
}]);



