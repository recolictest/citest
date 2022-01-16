package minecrafttransportsimulator.blocks.tileentities.components;

import minecrafttransportsimulator.baseclasses.Point3dPlus;
import minecrafttransportsimulator.blocks.tileentities.instances.TileEntityDecor;
import minecrafttransportsimulator.entities.instances.PartInteractable;
import minecrafttransportsimulator.mcinterface.InterfacePacket;
import minecrafttransportsimulator.mcinterface.WrapperNBT;
import minecrafttransportsimulator.mcinterface.WrapperPlayer;
import minecrafttransportsimulator.mcinterface.WrapperWorld;
import minecrafttransportsimulator.packets.instances.PacketTileEntityLoaderConnection;

public abstract class ATileEntityLoader extends TileEntityDecor{
	public PartInteractable connectedPart;

    public ATileEntityLoader(WrapperWorld world, Point3dPlus position, WrapperPlayer placingPlayer, WrapperNBT data){
		super(world, position, placingPlayer, data);
    }
	
    @Override
	public boolean update(){
		if(super.update()){
			//Do load/unload checks.  Checks only occur on servers.  Clients get packets for state changes.
			if(!world.isClient()){
				if(connectedPart == null){
					//Check for a new part every second.  We don't want every tick as this would increase server loads.
					if(ticksExisted%20 == 0){
						updateNearestPart();
					}
				}else{
					//Don't load parts that don't exist.
					//Also check distance to make sure the part hasn't moved away.
					if(!connectedPart.isValid || connectedPart.position.distanceTo(position) > 10){
						updateNearestPart();
					}
				}
	
				//If we have a connected part, try to load or unload from it depending on our state.
				if(connectedPart != null){
					if(isUnloader()){
						doUnloading();
					}else{
						doLoading();
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	protected void updateNearestPart(){
		PartInteractable nearestPart = null;
		double nearestDistance = 999;
		if(canOperate()){
			for(PartInteractable interactablePart : world.getEntitiesOfType(PartInteractable.class)){
				if(canLoadPart(interactablePart)){
					if(interactablePart.position.distanceTo(position) < nearestDistance){
						nearestPart = interactablePart;
					}
				}
			}
		}
		if(nearestPart != null){
			connectToPart(nearestPart);
			InterfacePacket.sendToAllClients(new PacketTileEntityLoaderConnection(this, true));
		}else if(connectedPart != null){
			InterfacePacket.sendToAllClients(new PacketTileEntityLoaderConnection(this, false));
			connectToPart(null);
		}
	}
	
	
	/**
	 *  Connects this loader to the specified part.  This should be done to change
	 *  the part the loader is connected to.  Do NOT modify the variable
	 */
	public void connectToPart(PartInteractable part){
		connectedPart = part;
	}
	
	public abstract boolean isUnloader();
	
	protected abstract boolean canOperate();
	
	protected abstract boolean canLoadPart(PartInteractable part);
	
	protected abstract void doLoading();
	
	protected abstract void doUnloading();
}
