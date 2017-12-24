<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
    <head>
        <#include "../head.ftl"/>
    </head>
    <body>
        <div class="container">
			<#include "../navbar.ftl" />

            <div class="main-content">
                <form action="/currency/convert" method="post">
                    <h1 class="header">Currency converter</h1>
                    <#if error??>
	                    <div class="alert alert-danger" role="alert">${error}</div>
                    </#if>
                    <div class="form-row">
                        <div class="form-group col-sm-12 col-md-3">
                            <@spring.bind "exchangeForm.currencyFrom"/>
                            <#assign fieldError>
                                <@spring.showErrors "<br/>"/>
                            </#assign>

                            <label>From</label>
                            <select id="currencyFrom" name="currencyFrom" class="form-control ${fieldError?has_content?then('is-invalid','')}">
                                <option value="">&nbsp;</option>
                                <#list currencies as currency>
                                    <#assign selectOption></#assign>
                                    <#assign disabledOption></#assign>
                                    <#if currency.currency == exchangeForm.currencyFrom!''>
                                        <#assign selectOption>selected="selected"</#assign>
                                    </#if>
	                                <#if exchangeForm.currencyTo?? && currency.currency == exchangeForm.currencyTo!''>
		                                <#assign disabledOption>disabled="disabled"</#assign>
	                                </#if>
                                    <option value="${currency.currency}" ${selectOption} ${disabledOption}>${currency.currency}&nbsp;(${currency.description})</option>
                                </#list>
                            </select>

                            <#if fieldError?has_content>
                                <div class="invalid-feedback">${fieldError}</div>
                            </#if>
                        </div>

                        <div class="form-group col-sm-12 col-md-3">
                            <@spring.bind "exchangeForm.currencyTo"/>
                            <#assign fieldError>
                                <@spring.showErrors "<br/>"/>
                            </#assign>

                            <label>To</label>
                            <select id="currencyTo" name="currencyTo" class="form-control ${fieldError?has_content?then('is-invalid','')}">
                                <option value="">&nbsp;</option>
                                <#list currencies as currency>
                                    <#assign selectOption></#assign>
	                                <#assign disabledOption></#assign>
                                    <#if currency.currency == exchangeForm.currencyTo!''>
                                        <#assign selectOption>selected="selected"</#assign>
                                    </#if>
	                                <#if exchangeForm.currencyFrom?? && currency.currency == exchangeForm.currencyFrom!''>
		                                <#assign disabledOption>disabled="disabled"</#assign>
	                                </#if>
                                    <option value="${currency.currency}" ${selectOption} ${disabledOption}>${currency.currency}&nbsp;(${currency.description})</option>
                                </#list>
                            </select>

                            <#if fieldError?has_content>
                                <div class="invalid-feedback">${fieldError}</div>
                            </#if>
                        </div>

                        <div class="form-group col-sm-12 col-md-3">
                            <@spring.bind "exchangeForm.amount"/>
                            <#assign fieldError>
                                <@spring.showErrors "<br/>"/>
                            </#assign>

                            <label>Amount</label>
                            <input name="amount" type="decimal" placeholder="Amount" class="form-control ${fieldError?has_content?then('is-invalid','')}" value="${exchangeForm.amount!''}" />

                            <#if fieldError?has_content>
                                <div class="invalid-feedback">${fieldError}</div>
                            </#if>
                        </div>

                        <div class="col-sm-12 col-md-3 convert-button-margin">
                            <button type="submit" class="btn btn-primary">Convert</button>
                        </div>
                    </div>
                </form>
	            <#if convertedValue??>
		            <div class="alert alert-primary" role="alert">
			            ${convertedValue.response}
		            </div>
	            </#if>

	            <#include "exchangeHistory.ftl"/>
            </div>
        </div>

	    <#include "../footer.ftl" />
	    <script src="/js/converter.js"></script>
    </body>
</html>
