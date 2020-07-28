<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 ">
			<div class="panel panel-primary">
				<div class="panel-heading">Add TO Cart</div>
				<div class="panel-body">
					<form:form method="post" modelAttribute="todo">
						<form:hidden path="id" />
						<fieldset class="form-group">
							<form:label path="userName">Name</form:label>
							<form:input path="userName" type="text" class="form-control"
								required="required" />
							<form:errors path="userName" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<form:label path="email">Email</form:label>
							<form:input path="email" type="text" class="form-control"
								required="required" />
							<form:errors path="email" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<form:label path="description">Product</form:label>
							<form:input path="description" type="text" class="form-control"
								required="required" />
							<form:errors path="description" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<form:label path="amount">Amount</form:label>
							<form:input path="amount" type="text" class="form-control"
								required="required" />
							<form:errors path="amount" cssClass="text-warning" />
						</fieldset>

						<button type="submit" class="btn btn-success">Save</button>
						
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="common/footer.jspf"%>