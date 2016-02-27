package com.wileynet.magicjewels.util;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;
import com.wileynet.magicjewels.Jewel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com
 */
public class JewelAccessor implements TweenAccessor<Jewel> {
	
	public static final int POS_XY = 1;
	public static final int CPOS_XY = 2;
	public static final int SCALE_XY = 3;
	public static final int ROTATION = 4;
	public static final int OPACITY = 5;
	public static final int TINT = 6;
	public static final int SIZE_XY = 7;

	@Override
	public int getValues(Jewel target, int tweenType, float[] returnValues) {
		//System.out.println("SpriteAccessor.getValues called");
		switch (tweenType) {
			case POS_XY:
				returnValues[0] = target.getX();
				returnValues[1] = target.getY();
				return 2;

			case CPOS_XY:
				returnValues[0] = target.getX() + target.getWidth()/2;
				returnValues[1] = target.getY() + target.getHeight()/2;
				return 2;

			case SCALE_XY:
				returnValues[0] = target.getScaleX();
				returnValues[1] = target.getScaleY();
				return 2;

			case ROTATION: returnValues[0] = target.getRotation(); return 1;
			case OPACITY: returnValues[0] = target.getColor().a; return 1;

			case TINT:
				returnValues[0] = target.getColor().r;
				returnValues[1] = target.getColor().g;
				returnValues[2] = target.getColor().b;
				return 3;
				
			case SIZE_XY:
				returnValues[0] = target.getWidth();
				returnValues[1] = target.getHeight();
				return 2;

			default: assert false; return -1;
		}
		
	}

	@Override
	public void setValues(Jewel target, int tweenType, float[] newValues) {
		//System.out.println("SpriteAccessor.setValues called");
		switch (tweenType) {
			case POS_XY: target.setPosition(newValues[0], newValues[1]); break;
			case CPOS_XY: target.setPosition(newValues[0] - target.getWidth()/2, newValues[1] - target.getHeight()/2); break;
			case SCALE_XY: target.setScale(newValues[0], newValues[1]); break;
			case ROTATION: target.setRotation(newValues[0]); break;

			case OPACITY:
				Color c = target.getColor();
				c.set(c.r, c.g, c.b, newValues[0]);
				target.setColor(c);
				break;

			case TINT:
				c = target.getColor();
				c.set(newValues[0], newValues[1], newValues[2], c.a);
				target.setColor(c);
				break;
				
			case SIZE_XY: target.setSize(newValues[0], newValues[1]); break;

			default: assert false;
		}
	}

}

