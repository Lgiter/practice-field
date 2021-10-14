[ <#list multiViewTest.view.dataList as item>     {     "viewName":"${item.name}"     }  <#if item_has_next>,       </#if> </#list>

,
<#list multiViewTest.project.dataList as item>  {               "projectName":"${item.name}"          }   <#if item_has_next>,</#if> </#list> ]