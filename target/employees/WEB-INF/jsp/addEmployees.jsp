<table>
	<tr>
		<th class="theader">Add Employees</th>
	</tr>
	<tr>
		<td>	
			<form method="post">
				<label>ID:</label><input type="text" name="employeeIdAdd" /><br/>
				<label>First Name:</label><input type="text" name="firstName" /><br/>
				<label>Last Name:</label><input type="text" name="lastName" /><br/>
				<label>DOB:</label><input type="text" name="date" placeholder="YYYY/MM/DD"/><br/>
				<input id="btn" type="submit" value="Add Employee" />
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<p>${ addDescription != null ? addDescription : "" }</p>
		</td>
	</tr>
</table>
