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
	//$scope.process = services.getCurrentProcess();
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

mexGED.controller('process-upload', ['$scope', '$http', '$state','services','ngDialog', function($scope, $http, $state,services,ngDialog) {
	$scope.newFile=false;
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
	services.getWorkflowDetail($stateParams.id).then(function(data){$scope.flow = data;});
	$scope.curretStep;
	
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
		
	$scope.add = function () {
		$scope.data1.push({id: $scope.data1.length + 1, name: 'Earl Knight'});
	};
	
	$scope.sortableCallback = function (sourceModel, destModel, start, end) { 
		console.log(start + ' -> ' + end);
		console.log($scope.data1);
		
	};
	
	$scope.openStep = function(step){
		$scope.curretStep = step;
		ngDialog.open({
			template: 'step-add',
			className: 'ngdialog-theme-default',
			scope: $scope 
		});
	}
	
	$scope.sortableOptions = {
		placeholder: '<div class="box-placeholder p0 m0"><div></div></div>',
		forcePlaceholderSize: true
	};
	
	$scope.showStatus = function() {
      var selected = $filter('filter')($scope.permissions, {value: $scope.curretStep.permission});
      return ($scope.curretStep.permission && selected.length) ? selected[0].text : 'Not set';
    };
	
}]);



