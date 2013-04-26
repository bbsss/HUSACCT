package husacct.analyse.task.analyser.csharp.generators;

import husacct.analyse.infrastructure.antlr.csharp.CSharpParser;
import java.util.Stack;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import husacct.analyse.task.analyser.VisibilitySet;

public class CSharpGeneratorToolkit {

    /**
     * Returns the parentname from the stack: IE stack is C.B.A -> "A.B.C"
     * @param parentStack
     */
    public static String getParentName(Stack<String> parentStack) {
        String result = "";
        for (String parentNamePart : parentStack) {
            result += parentNamePart + ".";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }

    /**
     * Inserts a dot when parentName is not empty
     * @param parentName
     */
    public static String potentiallyInsertDot(String parentName) {
        return parentName.length() > 0 ? "." : "";
    }

	/**
	 * Concatenates two strings and inserds a dot when parentName != null
	 * @param parentName 
	 * @param name
	 */
    public static String getUniqueName(String parentName, String name) {
        return parentName + potentiallyInsertDot(parentName) + name;
    }

	public static String belongsToClass(String namespaces, String classes) {
        return getUniqueName(namespaces, classes);
    }
	
	public static String belongsToClass(Stack<String> namespaceStack, Stack<String> classStack) {
        String namespaces = getParentName(namespaceStack);
        String classes = getParentName(classStack);
        return getUniqueName(namespaces, classes);
    }
	
    public static boolean isAbstract(Tree tree) {
        CommonTree ct = (CommonTree) tree;
        CommonTree modifierList = (CommonTree) ct.getFirstChildWithType(CSharpParser.MODIFIERS);
        if (modifierList == null || modifierList.getChildCount() < 1) {
            return false;
        } else {
            return modifierList.getFirstChildWithType(CSharpParser.ABSTRACT) != null;
        }
    }
	
    public static String getVisibility(Tree tree) {
        CommonTree ct = (CommonTree) tree;
        CommonTree modifierList = (CommonTree) ct.getFirstChildWithType(CSharpParser.MODIFIERS);
        if (modifierList == null || modifierList.getChildCount() < 1) {
            return VisibilitySet.DEFAULT.toString();
        } else {
            String found = modifierList.getChild(0).toString();
            if (VisibilitySet.isValidVisibillity(found)) {
                return found;
            } else {
                return VisibilitySet.DEFAULT.toString();
            }
        }
    }
	
	public static void deleteTreeChild(Tree treeNode) {
        for (int child = 0; child < treeNode.getChildCount();) {
            treeNode.deleteChild(treeNode.getChild(child).getChildIndex());
        }
    }
	
	public static String createCommaSeperatedString(Stack<String> names) {
		String result = "";
		for (String parentNamePart : names) {
			result += parentNamePart + ",";
		}
		return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
	}
}
