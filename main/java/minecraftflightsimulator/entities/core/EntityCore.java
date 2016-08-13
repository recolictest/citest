package minecraftflightsimulator.entities.core;

import net.minecraft.world.World;

/**Core entities are like children except they cannot be removed from a parent.
 * Used primarily as bounding-box extensions for collision detection and impact calculations.
 * 
 * @author don_bruce
 */
public class EntityCore extends EntityChild{
	
	public EntityCore(World world) {
		super(world);
	}

	public EntityCore(World world, EntityParent parent, String parentUUID, float offsetX, float offsetY, float offsetZ, float width, float height){
		super(world, parent, parentUUID, offsetX, offsetY, offsetZ, width, height, 0);
	}
}
