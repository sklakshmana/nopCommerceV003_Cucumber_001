Feature: Customers

Scenario: Search Customer by EmailID
	Given User Launch Chrome browser
	When User opens URL "http://admin-demo.nopcommerce.com/login"
	And User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login
	Then User can view Dashboard
	When User click on customers Menu
	And click on customers Menu Item
	And Enter customer Email
	When Click on search button
	Then User should found Email in the search table
	And close browser