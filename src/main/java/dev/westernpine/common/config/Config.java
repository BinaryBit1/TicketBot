package dev.westernpine.common.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.yaml.snakeyaml.DumperOptions.FlowStyle;

import lombok.Getter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.json.JSONConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public class Config {
	
	public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    @Getter
    private ConfigBuilder builder;
    @Getter
    private ConfigurationLoader<?> loader;
    @Getter
    private ConfigurationNode config;

    @Getter
    private String folder;
    // File name without extension
    private String fileName;
    @Getter
    private File file;

    /*
     * Config can only be initialized through builder method. Protected...
     */
    private Config(ConfigBuilder builder) {
        this.builder = builder;
        this.loader = builder.loader;
        this.folder = builder.folder;
        this.fileName = builder.fileName;
        this.file = new File(folder, fileName);

        new File(folder).mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        load(); // Set the config(ConfigurationNode) object.
    }

    /*
     * Gets file name (With / Without extension)
     */
    public String getFileName() {
        return file.getName();
    }

    /*
     * Loads the file in the Loader format.
     */
    public void load() {
        try {
            config = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Saves the Loaded information to the file.
     */
    public void save() {
        try {
            loader.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Checks if the path exists in the file or not.
     */
    public boolean exists(String path) {
        return !compileNode(path).isVirtual();
    }

    /*
     * Gets a list of all the imediate child paths of a section.
     */
    public List<String> getChildNodes(String path) {
        List<String> child = new ArrayList<>();
        compileNode(path).getChildrenMap().entrySet().stream().forEach(node -> child.add(node.getKey().toString()));
        return child;
    }

    /*
     * Translates the string path to the Virtual config node path
     */
    public ConfigurationNode compileNode(String path) {
        String[] nodePart = path.split("\\.");
        int last = nodePart.length - 1;
        ConfigurationNode node = config.getNode(nodePart[0]);
        for (int holder = 1; holder <= last;) {
            node = node.getNode(nodePart[holder]);
            holder++;
        }
        return node;
    }

    /*
     * Sets a value at the specified path. creates a path if none is detected.
     * For general values (int, double, boolean, string, etc...)
     */
    private void set(ConfigurationNode node, Object value) {
        node.setValue(value);
        if(builder.updating) {
            save();
            load();
        }
    }

    /*
     * Sets a value at the specified path if no value exists. creates a path if
     * none is detected. For general values (int, double, boolean, string,
     * etc...)
     */
    private void setIfNeeded(ConfigurationNode node, Object value) {
        if (node.isVirtual()) {
            node.setValue(value);
            if(builder.updating) {
                save();
                load();
            }
        }
    }

    /*
     * FOR GENERAL VALUES
     */

    /*
     * Retrieves values stored at the specified path. creates a path and adds a
     * default value if none is detected. For general values (int, double,
     * boolean, string, etc...)
     */
    public Object get(String path, Object defaultValue) {
        ConfigurationNode node;
        if (path.contains("."))
            node = compileNode(path);
        else
            node = config.getNode(path);

        setIfNeeded(node, defaultValue);
        return node.getValue();
    }

    /*
     * Sets a value at the specified path. creates a path if none is detected.
     * For general values (int, double, boolean, string, etc...)
     */
    public void set(String path, Object value) {
        ConfigurationNode node;
        if (path.contains("."))
            node = compileNode(path);
        else
            node = config.getNode(path);
        set(node, value);
    }

    /*
     * For Special Types ;]
     */

    /*
     * Gets a String list from the specified path. If no list exists, a default
     * list is set. If a value from the list is null, it will be replaced with
     * the replacement.
     */
    public <T> List<T> getList(String path, List<T> defaultValue, T nullReplacement) {
        List<T> toReturn = new ArrayList<T>();
        Function<Object, T> typeTranny = new Function<Object, T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T apply(Object input) {
                return (input != null) ? (T) input : (T) nullReplacement;
            }
        };
        if (path.contains(".")) {
            ConfigurationNode node = compileNode(path);
            setIfNeeded(node, defaultValue);
            toReturn = node.getList(typeTranny);
        } else {
            ConfigurationNode node = config.getNode(path);
            setIfNeeded(node, defaultValue);
            toReturn = node.getList(typeTranny);
        }
        if (defaultValue != null) {
            if (toReturn == null) {
                toReturn = defaultValue;
                set(path, defaultValue);
            }
            if (toReturn.isEmpty() && !defaultValue.isEmpty()) {
                toReturn = defaultValue;
                set(path, defaultValue);
            }
        }
        List<T> returning = new ArrayList<>();
        toReturn.stream().forEach(type -> returning.add(type));
        return returning;

    }
	
    public static class ConfigBuilder {

	    /*
	     * Getter methods not needed, as variables are being retrieved through
	     * Config object
	     */
	    protected ConfigType type;

	    /*
	     * Will be set in the #build() method, otherwise null;
	     */
	    protected ConfigurationLoader<?> loader;
	    
	    /*
	     * Determines if config file will update when methods are called.
	     */
	    protected boolean updating;

	    /*
	     * ACTUAL file information
	     */
	    protected String folder;
	    protected String fileName;

	    /*
	     * Only accessible by Config.builder(); Initializes and sets up variables.
	     */
	    private ConfigBuilder() {
	        type = ConfigType.YAML;
	        updating = true;
	        folder = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile()
	                .toString();
	        fileName = "config.yml";
	    }
	    public Config build() {
	        type.getSetLoader().accept(this);
	        return new Config(this);
	    }

	    /*
	     * Builder methods
	     */
	    public ConfigBuilder configType(ConfigType type) {
	        this.type = type;
	        return this;
	    }
	    public ConfigBuilder updating(boolean updating) {
	        this.updating = updating;
	        return this;
	    }
	    public ConfigBuilder folder(String folder) {
	        this.folder = folder;
	        return this;
	    }
	    public ConfigBuilder fileName(String fileName) {
	        this.fileName = fileName;
	        return this;
	    }

	    /*
	     * Using local methods to ensure proper translation of items. ie: File name
	     * needs to include a file extension.
	     */
	    public ConfigBuilder file(File file) {
	        folder(file.getParent());
	        fileName(file.getName());
	        return this;
	    }
	}
	
	public enum ConfigType {
	    YAML(builder -> {
	        builder.loader = YAMLConfigurationLoader.builder().setFlowStyle(FlowStyle.BLOCK)
	                .setPath(new File(builder.folder, builder.fileName).toPath()).build();
	    }), JSON(builder -> {
	        builder.loader = JSONConfigurationLoader.builder().setPath(new File(builder.folder, builder.fileName).toPath())
	                .build();
	    }), GSON(builder -> {
	        builder.loader = GsonConfigurationLoader.builder().setPath(new File(builder.folder, builder.fileName).toPath())
	                .build();
	    }), HOCON(builder -> {
	        builder.loader = HoconConfigurationLoader.builder().setPath(new File(builder.folder, builder.fileName).toPath())
	                .build();
	    }),;

	    /**
	     * Sets the ConfigBuilder loader variable. Used in ConfigBuilder#build()
	     * method to construct the configuration loader object inside the
	     * ConfigBuilder class... which is also set in the Config class after the
	     * ConfigBuilder#build() is ran.
	     */
	    @Getter
	    private Consumer<ConfigBuilder> setLoader;
	    ConfigType(Consumer<ConfigBuilder> setLoader) {
	        this.setLoader = setLoader;
	    }
	}
	
	

}