Feature: Customers

Background:Below are the common steps for each scenario
Given User Launch Chrome browser
When User opens URL "http://admin-demo.nopcommerce.com/login" 
And User enters Email as "admin@yourstore.com" and Password as "admin"
And Click on Login 
Then User can view Dashboard 

@sanity
Scenario: Add a new customer
When User clicks on customers menu
And click on customers menu item
And click on add new button
Then user can view add new customer page
When user enter customer info
And click on save button
Then user can view confirmation message "The new customer has been added sucessfully"
And close browser

@Regression
Scenario: Search Customer by EmailId
When User clicks on customers menu
And click on customers menu item
And enter customer Email
When click on search button
Then user should found Email in the search table
And close browser


@sanity
Scenario: Search Customer by name
When User clicks on customers menu
And click on customers menu item
And enter customer Firstname
And enter customer Lastname
When click on search button
Then user should found Name in the search table
And close browser

