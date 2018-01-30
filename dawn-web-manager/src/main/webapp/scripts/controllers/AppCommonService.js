var option = {};
option.url = {
	'api_admin' : '/admin/',
	'api_account' : '/account/',
	'api_user' : '/user/',
	'api_accountrecord' : '/accountrecord/',
	'api_product' : '/product/',
	'api_creditor' : '/creditor/',
	'api_match' : '/matchManagement/',
	'api_message' : '/',
	'api_UsersFunds' : '/UsersFunds/'
};
option.header = {
	'headers' : {
		'Content-Type' : 'application/x-www-form-urlencoded;charset=UTF-8;'
	}
};
angular
		.module('AppCommonService', [ 'ngCookies' ])
		.factory(
				'AdminService',
				function($http) { //将$http注入，就可以向服务器发送请求
					return {
						signIn : function($str) {
							//alert($str);
							return $http.post(option.url.api_account + 'login'+'.do',
									$str, option.header);
							// /account/login
						}
					}
				})

