package org.example.Lesha_REST;

import org.example.Lesha_REST.service.GlobalService;
import org.example.Lesha_REST.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LeshaRestApplication {
	static String[] args;

	@Autowired
	private GlobalService globalService;

	public static void main(String[] args) {
		LeshaRestApplication.args = args;
		SpringApplication.run(LeshaRestApplication.class, args);
	}

	@PostConstruct
	public void init(){

		CLIArguments arguments;
		try {
			arguments = CLIArguments.parseArgs(LeshaRestApplication.args);
		} catch (CLIArguments.InvalidArgsException e) {
			System.out.println("Invalid CLI arguments "+e);
			return;
		}

		CLIArguments.CLIArgument logsArg = arguments.getArgOrNull("log");

		if(logsArg!=null){
			Loggers.createFileHandler(logsArg.getValue());
		}

		CLIArguments.CLIArgument initDataArg = arguments.getArgOrNull("initdata");

		if(initDataArg!=null){
			DBDataLoader.loadData(globalService, initDataArg.getValue(), initDataArg.getFlagValueOrNull("force"));
		}
	}
}
