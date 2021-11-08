
[
<#list mulit_api_test01.total.dataList as item>
    {
    "first_detect_intime_rate":${item.pdi_intime_rate}
    }
    <#if item_has_next>,</#if>
</#list>
]