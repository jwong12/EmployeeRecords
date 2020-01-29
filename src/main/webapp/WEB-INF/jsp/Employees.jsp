<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
	<tr>	
		<th class="theader">Employees List</th>
	</tr>
	<tr>
		<td>
			<table>
				<tr class="headers">
					<th>ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>DOB</th>
				</tr>
				<c:forEach items="${ employeeList }" var="employee">
					<tr class="cells">
						<td>${ employee.employeeID }</td>
						<td>${ employee.firstName }</td>
						<td>${ employee.lastName }</td>
						<td>${ employee.dateOfBirth }</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
