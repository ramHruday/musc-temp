package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;

import cn.edu.nju.mutestdemo.EnumType.MuType;

public class ModifierInvoc extends Unit {
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    private Object[] arguments;

    @Override
    public void output() {
        System.out.print(getName());
        if (arguments != null && arguments.length > 0) {
            System.out.print("(");
            Argument.ListOutput(arguments);
            System.out.print(")");
        }
    }

    public String outputToLine(ArrayList<MuType> types) {
        String str = getName();
        if (arguments != null && arguments.length > 0) {
            str += "(";
            Statement.lineContent += str;
            str += Argument.ListOutputToLine(types, arguments);
            str += ")";
            Statement.lineContent += ")";
        }
        return str;
    }

}
