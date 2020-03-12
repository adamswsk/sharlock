// Copyright 2015 Quuppa Oy. All rights reserved.
var Q = Q || {};
Q.settings = function() {
	var that = {};
	that.useSmoothing = true;
	//that.smoothing = 0.8;
	that.maxAge = 80000;
	that.centerOnSelected = false;
	that.showRawDataInTree = false;
	that.renderCovarianceForSelected = true;
	that.renderInactiveAreaAsGrey = true;
	that.renderZones = true;
	that.renderTrackingAreaBorders = true;
	that.dotScaleFactor = 1.0;
	that.tagPositionAccuracyEnabled = true;
	that.tagPositionRadiusThreshold = 10.0;
	that.gridVisible = true;
	that.gridColor = "#ff0000";
	that.backgroundColor = "#898484";
	that.gridAlpha = 0.5;
	
	options = new function () {
            this.batchNo ='';this.qty = 0;this.qtyUom ='';//this.qty2 = 0;
            this.路径查看 = function() {
                    window.open("path.html");
                };
        };
        var gui = new dat.GUI();
        gui.domElement.style = 'position:absolute;top:10px;right:0px;height:600px';
        gui.add(options, 'batchNo').name("人员编号：").listen();
        //gui.add(options, 'qty').name("违规次数：").listen();
        gui.add(options, 'qtyUom').name("当前事件：").listen();
        //gui.add(options, 'qty2').name("件数：").listen();
        gui.add(options, '路径查看');
	
	return that;
}();
