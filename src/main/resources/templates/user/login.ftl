<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "head.ftl"/>
	</head>

	<body>
		<div class="container">

			<form class="form-signin" action="/login" method="post">
				<h2 class="form-heading">Please sign in</h2>
				<label class="sr-only">Email address</label>
				<input name="email" type="email" class="form-control" placeholder="Email address" required autofocus/>

				<label class="sr-only">Password</label>
				<input name="password" type="password" class="form-control" placeholder="Password" required/>

				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
				<a class="btn btn-secondary btn-lg btn-block" href="/registration">Registration</a>
			</form>

		</div>
	</body>
</html>
