<#import "parts/common.ftlh" as c>

<@c.page>
	<form method="get" action="/main" class="row row-cols-lg-auto g-3 align-items-center mb-3">
		<div class="col-12">
			<input type="text" name="tag" value="${tag?ifExists}" class="form-control" placeholder="Tag">
		</div>
		<div class="col-12">
			<button type="submit" class="btn btn-outline-primary">Search</button>
		</div>
	</form>
	<a class="btn btn-primary mb-3" data-bs-toggle="collapse" href="#collapseExample" role="button"
	   aria-expanded="false" aria-controls="collapseExample">
		Add new message
	</a>
	<div class="collapse" id="collapseExample">
		<div class="card card-body">
			<form method="post" action="/main/add" enctype="multipart/form-data">
				<input type="hidden" name="_csrf" value="${_csrf.token}">
				<div class="mb-3">
					<label for="formGroupExampleInput" class="form-label">Message</label>
					<input type="text" name="text" class="form-control" id="formGroupExampleInput"
					       placeholder="Input message">
				</div>
				<div class="mb-3">
					<label for="formGroupExampleInput" class="form-label">Tag</label>
					<input type="text" name="taf" class="form-control" id="formGroupExampleInput"
					       placeholder="Input tag">
				</div>
				<div class="mb-3">
					<label for="formGroupExampleInput" class="form-label">File</label>
					<input type="file" name="file" class="form-control" id="formGroupExampleInput"
					       placeholder="Choose file">
				</div>
				<button type="submit" class="btn btn-primary">Add</button>
			</form>
		</div>
	</div>

	<div class="row row-cols-1 row-cols-md-2 g-4">
        <#list messages as message>
			<div class="card my-3 mx-3" style="width: 18rem;">
                <#if message.filename??>
					<img src="/img/${message.filename}" class="card-img-top">
                </#if>
				<div class="m-2">
					<span>${message.text}</span>
					<i>${message.tag}</i>
				</div>
				<div class="card-footer text-muted">
                    ${message.authorName}
				</div>
			</div>
        <#else>
			No messages
        </#list>
	</div>
</@c.page>