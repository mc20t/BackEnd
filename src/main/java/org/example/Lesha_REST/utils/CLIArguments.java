package org.example.Lesha_REST.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLIArguments {
    public static class CLIArgument {
        private String name;
        private String value;
        private Map<String, Object> flags;

        public CLIArgument(String name, String value, Map<String, Object> flags) {
            this.name = name;
            this.value = value;
            this.flags = flags;
        }

        public CLIArgument(String name, String value) {
            this.name = name;
            this.value = value;
            this.flags = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public Object getFlagValueOrNull(String key) {
            try {
                return flags.get(key);
            } catch (NullPointerException e) {
                return null;
            }
        }

        public void putFlag(String key, Object value) {
            flags.put(key, value);
        }

        @Override
        public String toString() {
            return "CLIArgument{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", flags=" + flags +
                    '}';
        }
    }

    public static class InvalidArgsException extends Exception {
        public InvalidArgsException() {
        }

        public InvalidArgsException(String s) {
            super(s);
        }
    }

    private Map<String, CLIArgument> argumentsMap;

    private CLIArguments(Map<String, CLIArgument> argumentsMap) {
        this.argumentsMap = argumentsMap;
    }

    private CLIArguments(List<CLIArgument> argumentList) {
        argumentsMap = new HashMap<>();
        for (CLIArgument arg : argumentList) {
            argumentsMap.put(arg.getName(), arg);
        }
    }

    public CLIArgument getArgOrNull(String key){
        try {
            return argumentsMap.get(key);
        }catch (NullPointerException e){
            return null;
        }
    }

    public static CLIArguments parseArgs(String[] args) throws InvalidArgsException {
        if(args == null){
            throw new InvalidArgsException("Args is null");
        }
        List<CLIArgument> argumentsList = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].startsWith("--")) {
                String argName = args[i].replaceFirst("--", "");
                if (i + 1 >= args.length) {
                    throw new InvalidArgsException("Invalid arg value " + argName);
                }
                String argValue = args[i + 1];
                CLIArgument argument = new CLIArgument(argName, argValue);
                int j = i + 1;
                while (j < args.length && !args[j].startsWith("--")) {
                    if (args[j].startsWith("-")) {
                        String flagName = args[j].replaceFirst("-", "");
                        if (j + 1 >= args.length || args[j + 1].startsWith("-")) {
                            argument.putFlag(flagName, true);
                        } else {
                            argument.putFlag(flagName, args[j + 1]);
                        }
                    }
                    ++j;
                }
                argumentsList.add(argument);
                i = j - 1;
            }
        }
        return new CLIArguments(argumentsList);
    }

//    public static void main(String[] args) {
////        String[] testArgs = new String[]{"--data", "data.json", "-overwrite", "--logs", "logs.txt", "-format", "json"};
//        String[] testArgs = new String[]{"--data", "data.json", "-overwrite", "--logs"};
//
//        try {
//            CLIArguments parsed = CLIArguments.parseArgs(testArgs);
//        } catch (InvalidArgsException e) {
//            System.out.println(e);
//        }
//    }
}
