<#setting date_format="dd/MM/yyyy">
<#assign dataNull = "01/01/1900" />
<#assign data1 = "14/05/2016" />
<#assign data2 = "20/05/2016" />
<#assign data3 = "19/06/2016" />
<#assign events_list = [
{"name":"Event Lorem", "date":data1?date},
{"name":"Event Lipsum", "date":data2?date},
{"name":"Event Free", "date":data2?date},
{"name":"Event Dolor", "date":data3?date},
{"name":"Event Sit", "date":data1?date}
] />




<h5>Order by date:</h5>
<#assign lastDate = dataNull?date>
<#list events_list?sort_by("date") as event>

    <#if lastDate != event.date >
        <p><b> ${event.date}</b></p>
    </#if>

    <#assign lastDate = event.date >
    <p>${event.name}: ${event.date}</p>

</#list>