<table>
	<tr>
		<th class="theader">Remove an Employee</th>
	</tr>
	<tr>
		<td>
			<form method="post">
				<label>ID:</label><input type="text" name="employeeIdRem" /><br/>
				<input id="btn" type="submit" value="Delete" />
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<p>${ remDescription != null ? remDescription : "" }</p>
		</td>
	</tr>
</table>
