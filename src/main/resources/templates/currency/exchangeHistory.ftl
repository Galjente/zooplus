<div>
	<h1 class="header">History</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Currency from</th>
				<th>Currency to</th>
				<th>Amount</th>
				<th>Exchange rate</th>
				<th>Exchanged amount</th>
				<th>Error message</th>
			</tr>
		</thead>
		<tbody>
		<#list historyList as history>
			<tr>
				<td>${history.currencyFrom}</td>
				<td>${history.currencyTo}</td>
				<td>${history.amount?string}</td>
				<td><#if history.exchangeRate??>${history.exchangeRate?string!''}</#if></td>
				<td><#if history.exchangedAmount??>${history.exchangedAmount?string!''}</#if></td>
				<td>${history.errorMessage!''}</td>
			</tr>
		</#list>
		</tbody>
	</table>
</div>