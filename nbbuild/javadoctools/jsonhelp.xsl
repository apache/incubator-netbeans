<?xml version="1.0" encoding="UTF-8"?>
<!--

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->

<!--
This DOCTYPE assumes that the pseudo-DTD given at the end of the
XSLT specification is present in the named location. It cannot be
committed to the repository for legal reasons. You need to download it:
<!DOCTYPE xsl:stylesheet [
<!ENTITY % result-elements "
| a | abbr | acronym | address | area
| b | base | bdo | big | blockquote
| body | br | button | caption | cite
| code | col | colgroup | dd | del
| dfn | div | dl | dt | em
| fieldset | form | h1 | h2 | h3
| h4 | h5 | h6 | head | hr
| html | i | img | input | ins
| kbd | label | legend | li | link
| map | meta | noscript | object | ol
| optgroup | option | p | param | pre
| q | samp | script | select | small
| span | strong | style | sub | sup
| table | tbody | td | textarea | tfoot
| th | thead | title | tr | tt
| ul | var
">
<!ENTITY % xsl-struct SYSTEM "xsl.dtd">
%xsl-struct;
]>
-->

<!-- TODO:
- in details of a change, list all branches it applies to
- create separate lists for all changes between one branch and the next
- clearly mark incompatible bits in details of a change, besides text contents
- warn about any changes made after a certain date which incl. additions but
  have no associated API version
- ability to display authors in pretty format
- kill $api-list and select from /apichanges/apidefs/apidef instead to get an
  API list (but the hack is still needed for index by affected class)
- whenever <hN> tags are used as part of a changelist template, the proper
  header level should be computed based on the surrounding context
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template name="apachenetbeanstext">
        <xsl:param name="maturity" />
        <xsl:choose>    
            <xsl:when test="$maturity = 'false'">
                <xsl:text>Apache NetBeans (incubating) </xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>Apache NetBeans </xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="apachenetbeansversion">
        <xsl:param name="maturity" />
        <xsl:param name="version" />
        
        <xsl:choose>    
            <xsl:when test="$version = '-'">
                <xsl:text>Current Development Version</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>    
                    <xsl:when test="$maturity = 'false'">
                        <xsl:text>Apache NetBeans (incubating) </xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>Apache NetBeans </xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:value-of select="$version"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="build-docmenu">
        <xsl:param name="menukey" />
        <div class="toc">
            <ul class="sectlevel1" data-responsive-menu="drilldown medium-dropdown">
                <li>
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'index'">
                            Documentation Overview
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="index.html">Documentation Overview</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
                <li>
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'apichanges'">
                            Changes since previous release
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="apichanges.html">Changes since previous release</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
                <li>
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'usecases'">
                            How to use certain APIs
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="usecases.html">How to use certain APIs</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
                <li> 
                    <a href="allclasses-frame.html">Index of all classes</a>
                </li>
                <li> 
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'layers'">
                            Extracted List of Layer APIs
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="layers.html">Extracted List of Layer APIs</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
                <li> 
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'properties'">
                            Extracted List of Property APIs
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="properties.html">Extracted List of Property APIs</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
                <li> 
                    <xsl:choose>    
                        <xsl:when test="$menukey = 'branding'">
                            Extracted List of Branding APIs
                        </xsl:when>
                        <xsl:otherwise>
                            <a href="branding.html">Extracted List of Branding APIs</a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
            </ul>
        </div>
    </xsl:template>
    <xsl:template name="htmlheader">
        <xsl:param name="title" />
        <xsl:param name="maturity" />
        <xsl:param name="version" />
        <head>
            <xsl:element name="title">
                <xsl:call-template name="apachenetbeansversion" >
                    <xsl:with-param name="maturity">
                        <xsl:value-of select="$maturity"/>
                    </xsl:with-param> 
                    <xsl:with-param name="version">
                        <xsl:value-of select="$version"/>
                    </xsl:with-param>
                </xsl:call-template>
                Documentation | <xsl:value-of select="$title" />
            </xsl:element>
            <link rel="stylesheet" href="netbeans.css" type="text/css"/>
            <link type="application/atom+xml" rel="alternate" href="apichanges.atom"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
            <meta name="msapplication-TileColor" content="#ffc40d" />
            <meta name="theme-color" content="#ffffff"/>
            <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
            <link rel="icon" type="image/png" sizes="32x32" href="//netbeans.apache.org/favicon-32x32.png" />
            <link rel="icon" type="image/png" sizes="16x16" href="//netbeans.apache.org/favicon-16x16.png" />
        </head>
    </xsl:template>
    <xsl:template name="htmlmainmenu">
        <xsl:param name="title" />
        <xsl:param name="maturity" />
        <xsl:param name="version" />
        <xsl:param name="releaseinfo" />
        <xsl:param name="menukey" />
        <div class="top-bar" id="responsive-menu">
            <div class='top-bar-left'>
                <a class='title' href="/">
                    <img src='apache-netbeans.svg' style='padding: 8px; height: 48px;' />
                    <xsl:call-template name="apachenetbeansversion" >
                        <xsl:with-param name="maturity">
                            <xsl:value-of select="$maturity"/>
                        </xsl:with-param> 
                        <xsl:with-param name="version">
                            <xsl:value-of select="$version"/>
                        </xsl:with-param>
                    </xsl:call-template>
                    Documentation | <xsl:value-of select="$title" />
                </a>
            </div>
            <div class='top-bar-rigtht scroll'>
                <xsl:variable name="currentversion" select="document($releaseinfo)/*/@position"/>
                <xsl:for-each select="document($releaseinfo)//release">
                    <xsl:sort data-type="number" select="@position" order="descending" />
                    <xsl:choose>
                        <xsl:when test="$currentversion = @position">
                            <span>
                                <xsl:value-of select="@version"/>
                            </span>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:element name="a">
                                <xsl:attribute name="class">apacheversion</xsl:attribute>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="@apidocurl"/><xsl:text>/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text></xsl:attribute>
                                <xsl:value-of select="@version"/>
                            </xsl:element>                 
                        </xsl:otherwise>
                    </xsl:choose>
                    
                </xsl:for-each>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/8.2/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    8.2
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/8.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    8.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/8.0.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    8.0.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/8.0/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    8.0
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.4/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.4
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.3.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.3.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.3/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.3
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.2.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.2.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.2/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.2
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.1.2/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.1.2
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.1.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.1.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                  7.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.0.1/javadoc/</xsl:text><xsl:value-of select="$menukey"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.0.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/7.0/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    7.0
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.9.1/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.9.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.9/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.9
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.8/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.8
                </xsl:element><xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.7/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.7
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.5/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.5
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.1/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://bits.netbeans.org/6.0/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    6.0
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://www.netbeans.org/download/5_5_1/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    5.5.1
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://www.netbeans.org/download/5_5/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    5.5
                </xsl:element>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>http://www.netbeans.org/download/5_0/javadoc/</xsl:text>
                        <xsl:value-of select="$menukey"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    5.0
                </xsl:element>
                    <!-- 
                    
                <li>
                    <a href="http://bits.netbeans.org/7.1.2/javadoc/" target="_top">7.1.2</a> - Javadoc as released for NetBeans IDE 7.1.2</li>
                <li>
                    <a href="http://bits.netbeans.org/7.1.1/javadoc/" target="_top">7.1.1</a> - Javadoc as released for NetBeans IDE 7.1.1</li>
                <li>
                    <a href="http://bits.netbeans.org/7.1/javadoc/" target="_top">7.1</a> - Javadoc as released for NetBeans IDE 7.1</li>
                <li>
                    <a href="http://bits.netbeans.org/7.0.1/javadoc/" target="_top">7.0.1</a> - Javadoc as released for NetBeans IDE 7.0.1</li>
                <li>
                    <a href="http://bits.netbeans.org/7.0/javadoc/" target="_top">7.0</a> - Javadoc as released for NetBeans IDE 7.0</li>
                <li>
                    <a href="http://bits.netbeans.org/6.9.1/javadoc/" target="_top">6.9.1</a> - Javadoc as released for NetBeans IDE 6.9.1</li>
                <li>
                    <a href="http://bits.netbeans.org/6.9/javadoc/" target="_top">6.9</a> - Javadoc as released for NetBeans IDE 6.9</li>
                <li>
                    <a href="http://bits.netbeans.org/6.8/javadoc/" target="_top">6.8</a> - Javadoc as released for NetBeans IDE 6.8</li>
                <li>
                    <a href="http://bits.netbeans.org/6.7/javadoc/" target="_top">6.7</a> - Javadoc as released for NetBeans IDE 6.7</li>
                <li>
                    <a href="http://bits.netbeans.org/6.5/javadoc/" target="_top">6.5</a> - Javadoc as released for NetBeans IDE 6.5</li>
                <li>
                    <a href="http://bits.netbeans.org/6.1/javadoc/" target="_top">6.1</a> - Javadoc as released for NetBeans IDE 6.1</li>
                <li>
                    <a href="http://bits.netbeans.org/6.0/javadoc/" target="_top">6.0</a> - Javadoc as released for NetBeans IDE 6.0</li>
                <li>
                    <a href="http://www.netbeans.org/download/5_5_1/javadoc/" target="_top">5.5.1</a> - Javadoc as released for NetBeans IDE 5.5.1</li>
                <li>
                    <a href="http://www.netbeans.org/download/5_5/javadoc/" target="_top">5.5</a> - Javadoc as released for NetBeans IDE 5.5</li>
                <li>
                    <a href="http://www.netbeans.org/download/5_0/javadoc/" target="_top">5.0</a> - Javadoc as released for NetBeans IDE 5.0</li>
                -->
            
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
