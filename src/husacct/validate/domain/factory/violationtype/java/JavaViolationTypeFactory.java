package husacct.validate.domain.factory.violationtype.java;

import husacct.validate.domain.ConfigurationServiceImpl;
import husacct.validate.domain.validation.ViolationType;
import husacct.validate.domain.validation.violationtype.java.JavaDependencyRecognition;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

class JavaViolationTypeFactory extends AbstractViolationType {
	private EnumSet<JavaDependencyRecognition> defaultDependencies = EnumSet.allOf(JavaDependencyRecognition.class);
	//private EnumSet<JavaAccessTypes> defaultAccess = EnumSet.allOf(JavaAccessTypes.class);	
	private static final String javaViolationTypesRootPackagename = "java";

	public JavaViolationTypeFactory(ConfigurationServiceImpl configuration){
		super(configuration);
		this.allViolationKeys = generator.getAllViolationTypes(javaViolationTypesRootPackagename);
	}

	@Override
	public List<ViolationType> createViolationTypesByRule(String ruleKey){
		if(isCategoryLegalityOfDependency(ruleKey)){
			return generateViolationTypes(defaultDependencies);
		}
		else{
			return Collections.emptyList();
		}
	}

	@Override
	public HashMap<String, List<ViolationType>> getAllViolationTypes(){
		return getAllViolationTypes(allViolationKeys);
	}
}