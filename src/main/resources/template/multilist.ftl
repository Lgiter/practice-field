[
<#list mulit_api_test01.day.dataList as item>
<#if item?index % 2 == 0>
    {
    "元素索引":${item?index},
    "avg_first_detect_intime_rate:avg中文名":${item.avg_first_detect_intime_rate},
    "pdi_intime_rate:pdi中文名":${item.pdi_intime_rate}
    }
    <#if item_has_next>,</#if>
</#if>


</#list>
]