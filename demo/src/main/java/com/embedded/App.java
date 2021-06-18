package com.embedded;

import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class App 
{
   public static void main( String[] args )
    {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();   

        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kContainer.getKieBase()).get(DMNRuntime.class);
        String namespace = "https://kiegroup.org/dmn/_C61C4369-2155-4E9A-A57C-A7AD9404CD12";
        String modelName = "age-classifier";

        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);
        DMNContext dmnContext = dmnRuntime.newContext();  

        for (Integer age : Arrays.asList(1,12,13,64,65,66)) {
            dmnContext.set("age", age);  
            DMNResult dmnResult =
                dmnRuntime.evaluateAll(dmnModel, dmnContext);  

            for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
                System.out.println("Age: " + age + ", " +
                        "Decision: '" + dr.getDecisionName() + "', " +
                        "Result: " + dr.getResult());
            }
        }
    }
}
