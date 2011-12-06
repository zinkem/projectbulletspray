/**
 * 
 */
package pbs;

import java.awt.geom.AffineTransform;

import jig.engine.ImageResource;
import jig.engine.RenderingContext;
import jig.engine.ViewableLayer;

/**
 * @author Skylar Hiebert
 *
 */
public class ScrollingBackgroundLayer implements ViewableLayer {
	private int layerWidth;
	private int layerHeight;
	
	ImageResource image;
	
	private double offset;
	private int scrollingSpeed;
	private int imgHeight;
	private int imgWidth;
	boolean active;
	
	/**
	 * Creates an <code>ImageBackgroundLayer</code> of a specified size, but
	 * without an actual image (the image must be specified at a later point in
	 * time). The layer is initially in an <code>inactive</code> state.
	 * 
	 * @param layerWidth
	 *            the width of the layer
	 * @param layerHeight
	 *            the height of the layer
	 * 
	 * @see #setBackground(ImageResource, boolean)
	 */
	public ScrollingBackgroundLayer(final int layerWidth, final int layerHeight) {
		active = false;
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
	}

	/**
	 * Creates an <code>ImageBackgroundLayer</code> which is immediately
	 * ready to be viewed. The layer is initially in an <code>active</code> 
	 * state.
	 * 
	 * @param img
	 *            the image resource to display on this layer
	 * @param layerWidth
	 *            the width of the layer
	 * @param layerHeight
	 *            the height of the layer
	 * @param mode
	 *            one of <code>SCALE_IMAGE</code>, 
	 *            <code>CENTER_IMAGE</code>, or <code>TILE_IMAGE</code>
	 *            describing what to do with images that are smaller
	 *            than the layer itself. 
	 * 
	 * @see #setBackground(ImageResource, boolean)
	 */
	public ScrollingBackgroundLayer(final ImageResource img,
			final int layerWidth, final int layerHeight, int scrollingSpeed) {
		
		image = img;
		imgHeight = img.getHeight();
		imgWidth = img.getWidth();
		this.layerHeight = layerHeight;
		this.layerWidth = layerWidth;
		this.scrollingSpeed = scrollingSpeed;
		active = true;
	}
	
	@Override
	public void render(RenderingContext rc) {
		if (!active) {
			return;
		}
		
		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
		
		for (int x = 0; x < layerWidth; x += imgWidth) {
			for (double y = layerHeight + offset; y > -(offset + imgHeight); y -= imgHeight) {
				at.setToTranslation(x, y);
				image.render(rc, at);
			}
		}

		
	}

	/**
	 * Updates the layer on each iteration of the game loop.
	 * This method is empty, as this image background layer is static.
	 *
	 * @param deltaMs
	 *               ignored
	 */
	public void update(final long deltaMs) {
		offset += scrollingSpeed * (deltaMs / 100.0);
		if(offset > imgHeight)
			offset = 0;
	}

	/**
	 * @return <code>true</code> iff the layer is in the active (visible)
	 * state.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the activation state of this layer.
	 * 
	 * @param a <code>true</code> iff the layer should be made active
	 */
	public void setActivation(final boolean a) {
		active = a;
	}
	
	public void setVelocity(int scrollingSpeed) {
		this.scrollingSpeed = scrollingSpeed;
	}

	public void setImageTile(ImageResource img) {
		this.image = img;
		imgHeight = img.getHeight();
		imgWidth = img.getWidth();
	}
}
