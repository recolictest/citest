package minecraftflightsimulator.planes.PZLP11;

import minecraftflightsimulator.containers.ContainerParent;
import minecraftflightsimulator.containers.GUIParent;
import minecraftflightsimulator.containers.SlotBucket;
import minecraftflightsimulator.containers.SlotEngineLarge;
import minecraftflightsimulator.containers.SlotFuel;
import minecraftflightsimulator.containers.SlotInstrument;
import minecraftflightsimulator.containers.SlotPilot;
import minecraftflightsimulator.containers.SlotPropeller;
import minecraftflightsimulator.containers.SlotSkid;
import minecraftflightsimulator.containers.SlotWheelLarge;
import minecraftflightsimulator.entities.core.EntityPlane;
import minecraftflightsimulator.helpers.InstrumentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPZLP11 extends EntityPlane{
	private static final ResourceLocation foregroundGUI = new ResourceLocation("mfs", "textures/planes/pzlp11/gui.png");
	private static final ResourceLocation backplateTexture = new ResourceLocation("mfs", "textures/planes/pzlp11/hud_backplate.png");
	private static final ResourceLocation moldingTexture = new ResourceLocation("mfs", "textures/planes/pzlp11/hud_moulding.png");
	
	public EntityPZLP11(World world){
		super(world);
	}
	
	public EntityPZLP11(World world, float posX, float posY, float posZ, float rotation, int textureCode){
		super(world, posX, posY, posZ, rotation, textureCode);
		this.displayName = "DietPepsi1997";
	}

	@Override
	protected void initPlaneProperties(){
		hasFlaps = false;
		maxFuel = 7000;		
		emptyMass=1150;
		momentRoll=1285;
		momentPitch=1825;
		momentYaw=2667;
		wingspan=11;
		wingArea=18;
		tailDistance=5;
		rudderArea=1F;
		elevatorArea=3;
		defaultElevatorAngle=-10F;
		initialDragCoeff=0.03F;
		dragAtCriticalAoA=0.12F;
		dragCoeffOffset = (float) ((dragAtCriticalAoA - initialDragCoeff)/Math.pow(15 - 0, 2));		
	}
	
	@Override
	protected void initChildPositions(){
		addCenterGearPosition(new float[]{0F, -0.3F, -5.25F});
		addLeftGearPosition(new float[]{-1.4F, -1.8F, 0.375F});
		addRightGearPosition(new float[]{1.4F, -1.8F, 0.375F});
		addEnginePosition(new float[]{0, -0.3F, 0.65F});
		addPropellerPosition(new float[]{0, -0.375F, 1.65F});
		addPilotPosition(new float[]{0, -.1F, -1.3F});
	}
	
	@Override
	public float[][] getCoreLocations(){
		return new float[][]{{0, -0.3F, 1}, {0, 0F, -5F}};
	}
	
	@Override
	public void drawHUD(int width, int height){
		InstrumentHelper.drawBasicHUD(this, width, height, backplateTexture, moldingTexture);
	}
	
	@Override
	public GUIParent getGUI(EntityPlayer player){
		return new GUIParent(player, this, foregroundGUI);
	}
	
	@Override
	public void initParentContainerSlots(ContainerParent container){
		container.addSlotToContainer(new SlotWheelLarge(this, 86, 113, 2));
		container.addSlotToContainer(new SlotSkid(this, 68, 113, 1));
		container.addSlotToContainer(new SlotWheelLarge(this, 50, 113, 4));
		container.addSlotToContainer(new SlotEngineLarge(this, 131, 62, 6));
		container.addSlotToContainer(new SlotPropeller(this, 149, 62, 10));
		container.addSlotToContainer(new SlotPilot(this, 113, 62));
		container.addSlotToContainer(new SlotBucket(this, 7, 113));
		container.addSlotToContainer(new SlotFuel(this, 7, 73));
		for(byte i=0; i<6; ++i){
			container.addSlotToContainer(new SlotInstrument(this, 7 + 18*(i%3), i < 3 ? 7 : 25, (i < 3 ? i + 1 : i + 3 ) + instrumentStartSlot));
		}
	}
}