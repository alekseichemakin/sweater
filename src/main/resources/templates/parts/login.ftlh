<#macro login path isRegistrForm>
	<form action="${path}" method="post">
		<div class="row mb-3">
			<label class="col-sm-2 col-form-label"> User Name : </label>
			<div class="col-sm-5">
				<input type="text" name="username" class="form-control" placeholder="Username"/>
			</div>
		</div>
		<div class="row mb-3">
			<label class="col-sm-2 col-form-label"> Password : </label>
			<div class="col-sm-5">
				<input type="password" name="password" class="form-control" placeholder="Password"/>
			</div>
		</div>
        <#if isRegistrForm>
			<div class="row mb-3">
				<label class="col-sm-2 col-form-label"> Email : </label>
				<div class="col-sm-5">
					<input type="email" name="email" class="form-control" placeholder="mail@mail.com"/>
				</div>
			</div>
        </#if>
		<input type="hidden" name="_csrf" value="${_csrf.token}">
        <#if !isRegistrForm><a href="/registration">Add new user</a></#if>
		<button type="submit" class="btn btn-primary"><#if !isRegistrForm>Sign in<#else>Create</#if></button>
	</form>
</#macro>

<#macro logout>
	<form action="/logout" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
		<button type="submit" class="btn btn-primary">Sign out</button>
	</form>
</#macro>