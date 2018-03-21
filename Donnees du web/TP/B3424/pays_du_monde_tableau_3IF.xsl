<?xml version="1.0" encoding="UTF-8"?>

<!-- New document created with EditiX at Tue Mar 13 16:09:31 CET 2018 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html"/>
	
	<xsl:template match="/">
		<html> 
			<head> 
				<title> 
				Pays du monde 
			  </title> 
			</head> 
			 
			<body style="background-color:white;">  
			   <h1>Les pays du monde</h1> 
				  Mise en forme par : Mohamed CHALLAL, Etienne DELAHAYE (B3424)
				<xsl:apply-templates select="//metadonnees" />
				<h2>Continents (regions)</h2>
				<xsl:for-each select="//country/infosRegion/region[not(. = ../../preceding-sibling::country/infosRegion/region)
									 and not(.='')]"> 
					<h3><xsl:value-of select="."/></h3>
					Sous-régions : 
					<xsl:for-each select="//infosRegion/subregion[current()/../region=../region 
																and not(. = ../../preceding-sibling::country/infosRegion/subregion )]"> 
						<xsl:value-of select="concat(., ' (')"/>
						<xsl:value-of select="concat(count(//subregion[.=current()]) , ' pays)')"/>
						<xsl:if test="not(position()=last())">
							 , 
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>
				<h3>Sans continent</h3>
				<xsl:value-of select="count(//country[infosRegion/region=''])"/> pays 
				<hr/>
				<p>Pays avec 7 voisins :
					<xsl:for-each select="//country[count(borders)=7]/name/common"> 
						<xsl:value-of select="."/>
						<xsl:if test="not(position()=last())">
							, 
						</xsl:if>
					</xsl:for-each>
				</p>
				<p>Pays ayant le plus long nom : 
					<xsl:for-each select="//country">
						<xsl:sort select="string-length(name/common)" data-type="number" order="ascending"/>
						<xsl:if test="position()=last()">
							<xsl:value-of select="name/common"/>
						</xsl:if>
					</xsl:for-each>
					</p>
				<hr/>
				<h1>Pays par ordre alphabétique</h1>
				<xsl:apply-templates select="countries" />
				
			</body> 
		</html> 
	</xsl:template>
	
	<xsl:template match="metadonnees">
		 <p style="text-align:center; color:blue;">
			Objectif : <xsl:value-of select="objectif"/>
		 </p><hr/>
	</xsl:template>
	
	<xsl:template match="countries">
		 <table width="100%" border="3" align="center">
			<tr>
                <th>N°</th>
                <th>Nom</th>
                <th>Capitale</th>
                <th>Continent<br/>Sous-continent</th>
                <th>Voisins</th>
                <th>Coordonnées</th>
                <th>Drapeau</th>
            </tr>
			<xsl:for-each select="country">
				<xsl:sort select="." order="ascending" data-type="text" lang="en" />
				<tr>
                <td><xsl:value-of select="position()"/></td>
				<xsl:apply-templates select="." />
				</tr>
			</xsl:for-each>
		 </table>
	</xsl:template>
	
	<xsl:template match="country">
				
				<td><span style="color:green"><xsl:value-of select="name/common"/></span> (<xsl:value-of select="name/official"/>)
					<xsl:if test="not(count(name/native_name[@lang='fra'])=0)">
						<br/><span style="color:brown"> Nom français : <xsl:value-of select="name/native_name[@lang='fra']/official"/></span>
					</xsl:if>
				</td>
                <td><xsl:value-of select="capital"/></td>
                <td><xsl:value-of select="infosRegion/region"/><br/><xsl:value-of select="infosRegion/subregion"/></td>
                <td>
					<xsl:for-each select="borders">
						<xsl:value-of  select="//country/name/common[../../codes/cca3 = current()]"/>
						<xsl:if test="not(position()=last())">, </xsl:if>
					</xsl:for-each>
				</td>
                <td>
                    Latitude : <xsl:value-of select="coordinates/@lat"/><br/> Longitude : <xsl:value-of select="coordinates/@long"/></td>
                <td><img src="{translate(concat('http://www.geonames.org/flags/x/',codes/cca2, '.gif'), 'AZERTYUIOPQSDFGHJKLMWXCVBN', 'azertyuiopqsdfghjklmwxcvbn')}" alt="" height="40" width="60" /></td>

	</xsl:template>
	

</xsl:stylesheet>


