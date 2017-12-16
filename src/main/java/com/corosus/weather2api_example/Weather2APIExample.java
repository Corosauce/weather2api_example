package com.corosus.weather2api_example;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Map;

@Mod(modid = "weather2api_example", name="weather2 api example", version="1.0")
public class Weather2APIExample {

    @Mod.Instance(value = com.corosus.weather2api_example.Weather2APIExample.modID)
    public static com.corosus.weather2api_example.Weather2APIExample instance;

    public static final String modID = "weather2api_example";
    public static final String weather2ModID = "weather2";

    public static boolean isWeather2Loaded = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Map<String, ModContainer> modMap = Loader.instance().getIndexedModList();
        isWeather2Loaded = modMap.containsKey(weather2ModID);
    }

}