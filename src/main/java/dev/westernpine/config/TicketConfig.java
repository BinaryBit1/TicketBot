package dev.westernpine.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import proj.api.marble.builders.config.Config;

public class TicketConfig {
    @Getter
    public Config config;

    private static String[] varEntries = {"BOT_TOKEN", "COMMAND_PREFIX", "SQL_IP", "SQL_PORT", "SQL_DATABASE", "SQL_USERNAME", "SQL_PASSWORD"};
    private List<String> toAdd = new ArrayList<>(Arrays.asList(varEntries));
    
    @Getter
    private HashMap<String, String> variables = new HashMap<>();

    public TicketConfig(String[] launchArgs, String configPath, String fileName) {
        varEntries = null;
        String string = " " + String.join(" ", launchArgs);

        if(string != null && string != "" && string.contains(" -")) { //makes sure there are launch arguements
            launchArgs = string.split(" -"); //splits the different arguements
            
            for (String arg : launchArgs) {
                
                if(arg.contains(" ") && arg.split(" ").length > 1) { //makes sure there is an id and value
                    String[] array = arg.split(" ");
                    
                    Optional<String> id = getVariable(false, array[0]); //the id/tag/argument such as id in "-id 0"
                    
                    if (id.isPresent()) 
                        variables.put(id.get(), (array.length > 1 ? String.join(" ", Arrays.copyOfRange(array, 1, array.length)) : array[1])); //removes/ignores id/tag/argument
                }
            }
        }
        
        filterAdditions();
        
        for(String key : System.getenv().keySet()) {
            Optional<String> id = getVariable(true, key);
            if(id.isPresent()) 
                variables.put(id.get(), System.getenv(key));
        }
        
        filterAdditions();
        
        Config config = Config.builder().file(new File(configPath, fileName)).build();
        for(String var : toAdd) {
            variables.put(var, (String)config.get(var, "?"));
        }
        config.save();
        config = null;
        toAdd = null;
    }

    private Optional<String> getVariable( boolean output, String id) {
        id = id.toUpperCase();
        for(String var : toAdd) {
            if(id.equalsIgnoreCase(var.toUpperCase())) {
                return Optional.of(var);
            }
        }
        return Optional.empty();
    }
    
    private void filterAdditions() {
        List<Object> toRemove = new ArrayList<>();
        for(String testing : toAdd) {
            for(String var : variables.keySet()) {
                if(var.equalsIgnoreCase(testing)) {
                    toRemove.add(testing);
                }
            }
        }
        for(Object remove : toRemove) {
            toAdd.remove(remove);
        }
    }
    
}