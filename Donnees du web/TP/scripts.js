
function ModifierCouleurArrierePlan(color){
	document.body.style.background = color;
}

function ModifierCouleurTexte(id, color){
	document.getElementById(id).style.color = color;
}

function AfficherLesDonneesDuPays(){
	var xsltProcessor = new XSLTProcessor();

    // Chargement du fichier XSL à l'aide de XMLHttpRequest synchrone 
    var xslDocument = chargerHttpXML("chercherPays.xsl");

    // Importation du .xsl
    xsltProcessor.importStylesheet(xslDocument);

    // Chargement du fichier XML à l'aide de XMLHttpRequest synchrone 
    var xmlDocument = chargerHttpXML("countriesTP2.xml");
	
	var name = document.getElementById("question3text").value;
	
    xsltProcessor.setParameter(null, "nomPays", name);
	
	
    // Création du document XML transformé par le XSL
    var newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);
	
	
	
	// Recherche du parent (dont l'id est "here") de l'élément à remplacer dans le document HTML courant
    var elementHtmlParent = window.document.getElementById("question3Result");
    // Premier élément fils du parent
    var elementHtmlARemplacer = recupererPremierEnfantDeTypeNode(elementHtmlParent);
    // Premier élément "elementName" du nouveau document (par exemple, "ul", "table"...)
    var elementAInserer = newXmlDocument.getElementById("dataQst3");

    // Remplacement de l'élément
    elementHtmlParent.replaceChild(elementAInserer, elementHtmlARemplacer);

}



function AfficherImageSVG(){
	// Chargement du fichier XML à l'aide de XMLHttpRequest synchrone 
    var xmlDocument = chargerHttpXML("exemple.svg");
	
	
	// Recherche du parent (dont l'id est "here") de l'élément à remplacer dans le document HTML courant
    var elementHtmlParent = window.document.getElementById("question4Result");
    // Premier élément fils du parent
    var elementHtmlARemplacer = recupererPremierEnfantDeTypeNode(elementHtmlParent);
    // Premier élément "elementName" du nouveau document (par exemple, "ul", "table"...)
    var elementAInserer = xmlDocument.getElementsByTagName("svg")[0];

    // Remplacement de l'élément
    elementHtmlParent.replaceChild(elementAInserer, elementHtmlARemplacer);

}


function rendreSVGClickable(){
	var image = document.getElementById("question4Result").getElementsByTagName("svg")[0];
	
	//var els = image.querySelectorAll("circle, path, rect");
	
	//	for(int i=0; i<els.length();++i){
	var els = image.children[0].children;
	//for(f in image.firstChild.children){
	for(var i=0; i<els.length; ++i){
		els[i].addEventListener('click', function(e) {
			window.document.getElementById("question5Result").innerHTML = 'ceci est ' + this.getAttribute("title");
		});

	}

}


function AfficherCarteDuMonde(){
	// Chargement du fichier XML à l'aide de XMLHttpRequest synchrone 
    var xmlDocument = chargerHttpXML("worldHigh.svg");
	
	
	// Recherche du parent (dont l'id est "here") de l'élément à remplacer dans le document HTML courant
    var elementHtmlParent = window.document.getElementById("question6Carte");
    // Premier élément fils du parent
    var elementHtmlARemplacer = recupererPremierEnfantDeTypeNode(elementHtmlParent);
    // Premier élément "elementName" du nouveau document (par exemple, "ul", "table"...)
    var elementAInserer = xmlDocument.getElementsByTagName("svg")[0];

    // Remplacement de l'élément
    elementHtmlParent.replaceChild(elementAInserer, elementHtmlARemplacer);

}


function rendreCarteDuMondeClickable(){
	var image = document.getElementById("question6Carte").getElementsByTagName("svg")[0];
	
	var els = image.children[1].children;
		
	for(var i=0; i<els.length; ++i){
		els[i].addEventListener('click', function(e) {
			window.document.getElementById("question7Result").innerHTML = 'ceci est ' + this.getAttribute("title");
		});

	}

}


function rendreCarteDuMondeTraversables(){
	
	var xsltProcessor = new XSLTProcessor();
    var xslDocument = chargerHttpXML("chercherPays.xsl");
    xsltProcessor.importStylesheet(xslDocument);
    var xmlDocument = chargerHttpXML("countriesTP2.xml");
	
	
	
	
	var image = document.getElementById("question6Carte").getElementsByTagName("svg")[0];
	
	var els = image.children[1].children;
		
	for(var i=0; i<els.length; ++i){
		els[i].addEventListener('mouseover', function(e) {
			this.style.fill = 'yellow';
			xsltProcessor.setParameter(null, "nomPays", this.getAttribute("title"));
			var newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);
			var elementHtmlParent = window.document.getElementById("question6Tableau");
			var elementHtmlARemplacer = recupererPremierEnfantDeTypeNode(elementHtmlParent);
			var elementAInserer = newXmlDocument.getElementById("data2");
			elementHtmlParent.replaceChild(elementAInserer, elementHtmlARemplacer);
		});
		els[i].addEventListener('mouseleave', function(e) {
			this.style.fill = '#cccccc';
		});

	}

}

