<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
<#include "head.ftl"/>
</head>

<body>
<div class="container">

    <form class="form-registration" method="POST" action="/registration">
        <h2 class="form-heading">Please fill all fields</h2>

	    <@spring.bind "registrationForm"/>
	    <#assign fieldError>
		    <@spring.showErrors "<br/>"/>
	    </#assign>
	    <#if fieldError?has_content>
		    <div class="alert alert-danger" role="alert">${fieldError}</div>
	    </#if>

	    <div class="form-group">
            <@spring.bind "registrationForm.email"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">Email address</label>
            <input name="email" type="email"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="Email address"
                   value="${registrationForm.email!''}"
                   autofocus required />
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>


        <div class="form-group">
            <@spring.bind "registrationForm.password"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">Password</label>
            <input name="password" type="password"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="Password"
                   value="${registrationForm.password!''}"
                   required />
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>

        <div class="form-group">
            <@spring.bind "registrationForm.repeatPassword"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">Repeat password</label>
            <input name="repeatPassword" type="password"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="Repeat password"
                   value="${registrationForm.repeatPassword!''}"
                   required />
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>

	    <div class="form-group">
            <@spring.bind "registrationForm.birthday"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <#if registrationForm.birthday??>
	            <#assign birthday>${registrationForm.birthday?string}</#assign>
            </#if>

                <label class="sr-only">Birthday(yyyy-MM-dd)</label>
                <input name="birthday" type="text"
                       class="form-control ${fieldError?has_content?then('is-invalid','')}"
                       placeholder="Birthday(yyyy-MM-dd)"
                       pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"
                       value="${birthday!''}"
                       required/>

            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
	    </div>

        <div class="form-group">
            <@spring.bind "registrationForm.country"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">Country</label>
            <select name="country"
                    class="form-control ${fieldError?has_content?then('is-invalid','')}">
                <option>Country</option>
                <#list countyList as country>
                    <#assign selectedOption></#assign>
                    <#if registrationForm.country?? && registrationForm.country == country>
                        <#assign selectedOption>selected="selected"</#assign>
                    </#if>
                    <option value="${country}" ${selectedOption}>${country}</option>
                </#list>
            </select>
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>

        <div class="form-group">
            <@spring.bind "registrationForm.city"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">City</label>
            <input name="city" type="text"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="City"
                   value="${registrationForm.city!''}"/>
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>

        <div class="form-group">
            <@spring.bind "registrationForm.address"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">Address</label>
            <input name="address"
                   type="text"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="Address"
                   value="${registrationForm.address!''}"/>
            <#if fieldError?has_content>
                <div class="invalid-feedback">${fieldError}</div>
            </#if>
        </div>

        <div class="form-group">
            <@spring.bind "registrationForm.zip"/>
            <#assign fieldError>
                <@spring.showErrors "<br/>"/>
            </#assign>
            <label class="sr-only">ZIP</label>
            <input name="zip" type="text"
                   class="form-control ${fieldError?has_content?then('is-invalid','')}"
                   placeholder="ZIP"
                   value="${registrationForm.zip!''}"/>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Registration</button>
    </form>

</div>
</body>
</html>
