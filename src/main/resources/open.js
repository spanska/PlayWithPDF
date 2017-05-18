var exportMyPDFAttachment = app.trustedFunction( function(cAttName, cFilePath){
	app.beginPriv();
	var oDoc = this.openDataObject(cAttName);
	oDoc.saveAs(cPath); oDoc.closeDoc();
	app.endPriv();
});

exportMyPDFAttachment("test.pdf","/tmp/myfile.pdf");
