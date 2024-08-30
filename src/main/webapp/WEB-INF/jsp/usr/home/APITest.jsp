<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST"></c:set>

<script>
	const API_KEY = '%2BKApk0ihIn7Any5BM3X7%2BnlJyc2yXA39m%2F%2BXn4uiAkLPB7wbUQENd3NzoFktNEchJlI1pyRmWXXZJhA%2FTNfeIg%3D%3D';
	
	async function getCData() {
		const url = 'https://apis.data.go.kr/B554035/tmr-forecast/get_tmr-forecast?pageNo=1&numOfRows=10&ilja=20240828&serviceKey='+ API_KEY;
		const response = await
		fetch(url);
		const data = await
		response.json();
		console.log("data", data);
		console.log("data.body", data.body);
		console.log("data.body.dataList", data.body.dataList);
		console.log("data.body.dataList[0]", data.body.dataList[0]);
		console.log("data.body.dataList[0].ygnm", data.body.dataList[0].ygnm);
	}
	
	async function getCData2() {
		const url = 'https://apis.data.go.kr/6300000/openapi2022/tasuInfo/gettasuInfo?pageNo=1&numOfRows=10&serviceKey='+ API_KEY;
		const response = await
		fetch(url);
		const data = await
		response.json();
		console.log("data", data);
		console.log("data.response", data.response);
		console.log("data.response.body", data.response.body);
		console.log("data.response.body.items", data.response.body.items);
		console.log("data.response.body.items[0]", data.response.body.items[0]);
		console.log("data.response.body.items[0].loCrdnt", data.response.body.items[0].loCrdnt);
		console.log("data.response.body.items[0].laCrdnt", data.response.body.items[0].laCrdnt);
		
	}
	
	getCData();
	getCData2();
	
</script>



<%@ include file="../common/head.jspf"%>
<%@ include file="../common/foot.jspf"%>