
<#assign lastGroup="0"/>
<#list list?sort_by("group") as event>

    名字:${event.name}
    组:${event.group}
</#list>