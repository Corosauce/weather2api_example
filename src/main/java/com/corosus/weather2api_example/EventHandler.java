package com.corosus.weather2api_example;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weather2.api.WeatherDataHelper;
import weather2.api.WindDataHelper;

public class EventHandler {

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            World world = DimensionManager.getWorld(0);
            if (world != null && world.getTotalWorldTime() % 20 == 0) {
                String test1 = "SERVER - isRaining at 0,0: " + isRainingAt(world, new BlockPos(0, 64, 0));
                String test2 = "SERVER - wind speed at 0,0: " + getWindSpeed(world, new BlockPos(0, 64, 0));
                System.out.println(test1);
                System.out.println(test2);

                EntityPlayer player = world.getClosestPlayer(0, 64, 0, -1, false);
                if (player != null) {
                    player.sendMessage(new TextComponentString(test1));
                    player.sendMessage(new TextComponentString(test2));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            World world = Minecraft.getMinecraft().world;
            if (world != null) {
                System.out.println("CLIENT - isRaining at 0,0: " + isRainingAt(world, new BlockPos(0, 64, 0)));
                System.out.println("CLIENT - wind speed at 0,0: " + getWindSpeed(world, new BlockPos(0, 64, 0)));
            }
        }
    }

    public boolean isRainingAt(World world, BlockPos pos) {
        if (Weather2APIExample.isWeather2Loaded) {
            return isRainingAtImpl(world, pos);
        } else {
            return world.isRainingAt(pos);
        }
    }

    public float getWindSpeed(World world, BlockPos pos) {
        if (Weather2APIExample.isWeather2Loaded) {
            return getWindSpeedImpl(world, pos);
        } else {
            return 0;
        }
    }

    @Optional.Method(modid=Weather2APIExample.weather2ModID)
    public boolean isRainingAtImpl(World world, BlockPos pos) {
        return WeatherDataHelper.isPrecipitatingAt(world, pos);
    }

    @Optional.Method(modid=Weather2APIExample.weather2ModID)
    public float getWindSpeedImpl(World world, BlockPos pos) {
        return WindDataHelper.getWindSpeed(world, pos);
    }

}
