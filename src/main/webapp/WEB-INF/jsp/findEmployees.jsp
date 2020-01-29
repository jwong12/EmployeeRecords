<table>
	<tr>
		<th class="theader">Find An Employee By ID</th>
	</tr>
	<tr>
		<td>
			<form method="post">
				<label>ID:</label><input type="text" name="employeeIdFind" /><br/>
				<input id="btn" type="submit" value="Search" />
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<p>${ employeeFound != null ? employeeFound : "" }</p>
			<p>${ findDescription != null ? findDescription : "" }</p>
		</td>
	</tr>
</table>
