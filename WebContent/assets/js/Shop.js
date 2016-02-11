//require('jquery-1-2.min.js')
// shop namespace
var shop = {};
shop.productList = new LinkedList();

//<editor-fold defaultstate="collapsed" desc="GET">
shop.searchProductsViaForm = function(){
	var searchText = $('#searchText').val();
	var categoryId = $('#categoryId').val();
	var limit = $('#limit').val();
	var isLimitedEdition = $('#isLimitedEdition').is(':checked');
	var isDiscounted = $('#isDiscounted').is(':checked')
	var sort = $('#sort').val();
	$('#btnSearch').prop("disabled",true);
	shop.getProductsBySearch(searchText,categoryId,isLimitedEdition,isDiscounted, sort,limit,
	function(data){ 
		$('#btnSearch').prop("disabled",false);
		if (data.success === true){
			shop.productList.clear();
			for(var i in data.payload){
				var prod = new Product();
				prod = shop.readAjaxObject(data.payload[i], prod);
				var dm = new DesignMeta();
				shop.readAjaxObject(data.payload[i].designMeta,dm);
				prod._designMeta = dm;
				shop.productList.AddLast(prod);
			}
			shop.renderShopProducts();
		}
	} 
	);
};
//-------------------- getProductsBySearch() -----------------------------------
/**
 * @param {String} searchText
 * @param {int} categoryId
 * @param {boolean} isLimitedEdition
 * @param {boolean} isDiscounted
 * @param {string} sort "NEW", "LIKED", "POPULAR", "PRICE_LOW", "PRICE_HIGH"
 * @param {function} callback
 * @returns {undefined}
 */
shop.getProductsBySearch = function(searchText, categoryId, isLimitedEdition, isDiscounted, sort,limit, callback){
	if(typeof(callback) !== 'function'){ 
		console.log("callback must be of type: 'function'"); return;
	}
	var data = 
	{ 
		'method' : 'getProductsBySearch', 
		'searchText' : searchText,
		'categoryId' : categoryId,
		'isLimitedEdition' : isLimitedEdition,
		'isDiscounted' : isDiscounted,
		'sort' : sort,
		'limit' : limit
	};
	$.get('./Products',data,
		function (json, status, xhr) {
			callback((json));
		},'json'
	);	
	var url = "./shop.jsp?";
	for(var name in data){
		url += name+"="+encodeURIComponent(data[name])+"&";
	}
	setVisiblePageURL(url);
};


//-------------------- getProductsByCategoryId() -------------------------------
/* @param {int} categoryId
 * @param {function} callback
 * @returns {undefined}
 */
shop.getProductsByCategoryId = function(categoryId, callback){
	if(typeof(callback) !== 'function'){ 
		console.log("callback must be of type: 'function'"); return;
	}
	
	$.get('./Products',
	{ 'method' : 'getProductsByCategoryId', 'categoryId':categoryId},
		function (json, status, xhr) {
			callback(json);
		},'json'
	);
};


//-------------------- getAllProducts() ----------------------------------------
/* @param {function} callback
 * @returns {undefined}
 */
shop.getAllProducts = function(callback){
	if(typeof(callback) !== 'function'){ 
		console.log("callback must be of type: 'function'"); return;
	}
	$.get('./Products',
	{ 'method' : 'getAllProducts'},
		function (json, status, xhr) {
			callback((json));
		},'json'
	);
};


//-------------------- getProductById() ----------------------------------------
/* @param {int} productId
 * @param {function} callback
 * @returns {undefined}
 */
shop.getProductById = function(productId, callback){
	if(typeof(callback) !== 'function'){ 
		console.log("callback must be of type: 'function'"); return;
	}
	$.get('./Products',
	{ 
		'method' : 'getProductById', 
		'productId' : productId
	},
		function (json, status, xhr) {
			callback((json));
		},'json'
	);
};

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Models">
function Product(){
	this._productId = null;
	this._categoryId = null;
	this._designMeta = new DesignMeta();
	this._productMinecraftName = "";
	this._productWebsiteName = "";
	this._priceTokens = 999999999999;
	this._isActive = true;
	this._description = "";
	this._isLimitedEdition = false;
	this._availableUntilTime = Math.max();
	this._isLimitedUses = false;
	this._numUses = 0;
	this._isAbleToExpire = false;
	this._timeBeforeExpiringMs = 0;
	this._commandsOnUse = "";
	this._commandsOnExpire = "";
};

function DesignMeta(){
	this._designMetaId = -1;
	this._material = "BARRIER";
	this._dataValue = 0;
	this._isEnchanted = false;
	this._imageUrl = "";
};
//</editor-fold>

shop.renderShopProducts = function(){
	var innerHtml = "";
	shop.productList.foreach(function(data){
		var p = new Product();
		console.log(data);
		p = shop.readAjaxObject(data,p);
		innerHtml += "<div class='col-sm-6 col-md-4' style='max-width:320px;'>";
		innerHtml += ("<div class='thumbnail' style='border: 1px solid;'>");
		innerHtml += ("<img src='"+p._designMeta._imageUrl+"' alt='...' style='height:200px;width:320px;border: 1px solid;'/>");
		innerHtml += ("<div class='caption'>");
		innerHtml += ("<h4>"+shop.htmlEncode(p._productWebsiteName)+"</h4>");
		innerHtml += ("<p>"+shop.htmlEncode(p._description)+"</p>");
		innerHtml += ("<p>Price: "+p._priceTokens+" tokens</p>");
		innerHtml += ("<p><a class='btn btn-primary' role='button' href='./BuyProduct?productId="+p._productId+"'>Buy</a>");
		innerHtml += ("<button class='btn btn-primary' role='button'onclick='alert('Liked!')'>+1 (203 Likes)</button>");
		innerHtml += ("</p>");
		innerHtml += ("</div>");
		innerHtml += ("</div>");
		innerHtml += ("</div>");
	});
	$('#shop-product-display').html(innerHtml);
};

shop.readAjaxObject = function(fromAjax,toObject){
	for (var key in fromAjax){
		if (key[0] === '_'){
			toObject[key] = fromAjax[key];
		}else{
			toObject["_"+key] = fromAjax[key];
		}
	}
	return toObject;
};
shop.htmlEncode = function(value) {
    return $('<div/>').text(value).html();
};

function setVisiblePageURL(urlPath){
     window.history.pushState({"html":"response.html","pageTitle":"response.pageTitle"},"", urlPath);
 }