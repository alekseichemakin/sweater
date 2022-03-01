<#import "parts/common.ftlh" as c>

<@c.page>
	${message?ifExists}
	<form action="/user/profile" method="post">
		<div class="row mb-3">
			<label class="col-sm-2 col-form-label"> User Name : </label>
			<div class="col-sm-5">
				<input type="text" name="username" class="form-control" placeholder="Username" value="${user.getUsername()}"/>
			</div>
		</div>
		<div class="row mb-3">
			<label class="col-sm-2 col-form-label"> Password : </label>
			<div class="col-sm-5">
				<input type="password" name="password" class="form-control" placeholder="Password"/>
			</div>
		</div>
			<div class="row mb-3">
				<label class="col-sm-2 col-form-label"> Email : </label>
				<div class="col-sm-5">
					<input type="email" name="email" class="form-control" placeholder="mail@mail.com" value="${user.getEmail()?ifExists}"/>
				</div>
			</div>
		<input type="hidden" name="_csrf" value="${_csrf.token}">
		<button type="submit" class="btn btn-primary">Save</button>
	</form>
</@c.page>