<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<div>
		<a type="button" class="btn btn-primary btn-md" href="/add-todo">Add
			to Cart</a>
	</div>
	<br>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3>List of TODO's</h3>
		</div>
		<div class="panel-body">
			<table class="table table-striped">
				<thead>
					<tr>
						<th width="40%">Product</th>
						<th width="40%">Amount</th>
						<th width="20%"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${todos}" var="todo">
						<tr>
							<td>${todo.description}</td>
							<td>${todo.amount}</td>
							<td><a type="button" class="btn btn-success"
								href="/update-todo?id=${todo.id}">Update</a> <a type="button"
								class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a>
								<form action="/charge" method="POST">
									<script src="https://checkout.razorpay.com/v1/checkout.js"
										data-key="rzp_test_v8dX4BVDcmCU39"
										data-amount=${todo.amount}
										data-order_id=${todo.orderid}	
										data-name="Daft Punk" data-description="Purchase Description"
										data-image="vk.jpg" data-netbanking="true"
										data-description=${todo.description}
										data-prefill.name=${todo.userName}
										data-prefill.email=${todo.email}
										data-prefill.contact="9999999999"}
										data-notes.shopping_order_id=${todo.id}>
									</script>
									<input type="hidden" name="shopping_order_id" value="21">
								</form></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</div>
<%@ include file="common/footer.jspf"%>