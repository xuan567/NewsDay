package com.example.newsday.util

object HtmlUtil {
    fun getStandard(html: String): String {
        return """<html> 
<head> 
<style type="text/css"> 
body {font-size:13px;}
</style> 
</head> 
<body><script type='text/javascript'>window.onload = function(){
var ${"$"}img = document.getElementsByTagName('img');
for(var p in  ${"$"}img){
 ${"$"}img[p].style.width = '100%%';
${"$"}img[p].style.height ='auto'
}
}</script>$html</body></html>"""
    }
}