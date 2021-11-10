package me.bryang.effectranks.debug;

import me.bryang.effectranks.PluginService;
import me.bryang.effectranks.EffectRanks;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugLogger{

    private final PluginService pluginService;
    private final EffectRanks plugin;

    private File file;

    public DebugLogger(PluginService pluginService){
        this.pluginService = pluginService;
        this.plugin = pluginService.getPlugin();
        setup();
        log("Loading plugin..");
    }

    public void log(String string, int number) {
        switch (number) {
            case 0:
                this.getLogger(string, "WARNING");
                break;

            case 1:
                plugin.getLogger().info("A error ocurred. Please check debug");
                this.getLogger(string, "ERROR");
                break;


            case 2:
                plugin.getLogger().info("Plugin successfully loaded!");
                this.getLogger(string, "SUCCESSFULL");
                break;
        }
    }

    public void setup(){
        file = new File(plugin.getDataFolder() ,"logs.yml");
        file.mkdirs();

        if (file.exists()){
            file.delete();
        }
        try{
            file.createNewFile();
            plugin.getLogger().info( "Logs created!");

        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    public void log(String string) {
        this.getLogger(string, "LOG");
    }


    private void getLogger(String string, String mode){
        Date now = new Date();
        try{
            FileWriter fw = new FileWriter(plugin.getDataFolder() + "/logs.yml", true);
            BufferedWriter writer = new BufferedWriter(fw);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

            writer.write("[" + format.format(now) + " " + mode + "] " + string);
            writer.newLine();
            writer.flush();
            writer.close();

        }catch (IOException io){
            io.printStackTrace();

        }

    }
}
