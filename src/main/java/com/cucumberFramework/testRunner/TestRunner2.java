package com.cucumberFramework.testRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@CucumberOptions(features = "src/test/resources/features/login/demo.feature", glue = { "src/main/java/com/cucumberFramework/stepdefinitions/stepdef3.java" }) 
@RunWith(Cucumber.class)
public class TestRunner2
{

}
