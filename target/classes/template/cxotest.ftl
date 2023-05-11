[
<#list results.A.frames as item>
    <#list item.data.values as data>
        <#if data?index == 1>
            <#list data as d>
                {
                "measure_key":"finishRateMonth",
                "measure_name":"本月工单解决率率",
                "value":${d?replace("%","")}
                }
            </#list>
        </#if>
    </#list>
</#list>
]