package tschallacka.mods.whoneedsbooks.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Class to communicate an action to a EntityLiving
 * @author mdibbets
 *
 */
public class PipedEntityPotionMessage implements IMessage {
    public int entity;
    public int dimension;
    public int potionID;
    public int potionDuration;
    public int potionAmplifier;
    public int special;
	public PipedEntityPotionMessage() {}

    public PipedEntityPotionMessage(int entity,int dimension, int potionID, int potionDuration, int potionAmplifier,int special) {
    	this.entity = entity;
    	this.dimension = dimension;
    	this.potionID = potionID;
    	this.potionDuration = potionDuration;
    	this.potionAmplifier = potionAmplifier;
    	this.special= special;
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(this.entity);
    	buf.writeInt(this.dimension);
    	buf.writeInt(this.potionID);
    	buf.writeInt(this.potionDuration);
    	buf.writeInt(this.potionAmplifier);
    	buf.writeInt(this.special);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	this.entity = buf.readInt();
    	this.dimension = buf.readInt();
    	this.potionID = buf.readInt();
    	this.potionDuration = buf.readInt();
    	this.potionAmplifier = buf.readInt();
    	this.special= buf.readInt();         
    }

}
