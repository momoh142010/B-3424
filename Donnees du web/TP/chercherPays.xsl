<?xml version="1.0" encoding="UTF-8"?>

<!-- New document created with EditiX at Tue Mar 13 16:09:31 CET 2018 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html"/>
	
	
	<xsl:param name="nomPays"/>
	
	
	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="//country[name/common=$nomPays]">
				<xsl:for-each select="//country[name/common=$nomPays]">
					<div id="dataQst3">
						<xsl:value-of select="concat('Official name : ', name/official)"/><br/>
						<xsl:value-of select="concat('Capital : ', capital)"/>
					</div>
					
					<div id="data2">
						<table>	
							<thead>
							<tr>	<th>Nom</th>
									<th>Capitale</th>
									<th>Drapeau</th></tr>
							</thead>
							<tbody>
									<tr>	<td id="TNomPays"><xsl:value-of select="name/official"/><br/></td>
											<td id="TCapitalePays"><xsl:value-of select="capital"/></td>
											<td id="TDrapeauPays"><img src="{translate(concat('http://www.geonames.org/flags/x/',codes/cca2, '.gif'), 'AZERTYUIOPQSDFGHJKLMWXCVBN', 'azertyuiopqsdfghjklmwxcvbn')}" alt="" height="40" width="60" /></td>
											
									</tr>
							</tbody>
						</table>
					</div>
				
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<div id="data">Ce pays n'a pas été trouvé dans la base de données.</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	
	
</xsl:stylesheet>


