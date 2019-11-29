package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Utils;

/**
 * A class to help build Labels. A mandatory argument is the text displayed in the label
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class  LabelBuilder {
    // Actor common attributes
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private float bx, by, bw, bh;
    private float width, height;
    private boolean visible;
    // Font style
    private TextButton.TextButtonStyle style;
    private String path;
    private Color color;
    private int size;
    // Label
    private String text;
    private int alignement;
    private boolean wrap;

    /**
     * Constructor of LabelBuilder, initialize variables
     * @param text the label text (mandatory)
     */
    public LabelBuilder(String text) {
        // Actor attributes
        this.eventListeners = new ArrayList<EventListener>();
        this.captureListeners = new ArrayList<EventListener>();
        this.x = 0;
        this.y = 0;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.width = -1;
        this.height = -1;
        this.visible = true;
        // Label attributes
        this.style = null;
        this.path = "COMMS.ttf";
        this.color = Color.BLACK;
        this.size = 60;
        this.text = text;
        this.alignement = Align.left;
        this.wrap = false;
    }

    // Common Actor attributes

    /**
     * Add a listener to this Label
     * @param listener the listener
     * @return this builderObject
     */
    public LabelBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public LabelBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public LabelBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Configure the bounds
     * @param boundX the boundary origin position x
     * @param boundY the boundary position y
     * @param boundWidth the width of the boundary, must be positive or 0
     * @param boundHeight the height of the boundary, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    /**
     * Configure the width
     * @param width, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public LabelBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    // Font style

    public LabelBuilder withStyle(TextButton.TextButtonStyle style) {
        this.style = style;
        return this;
    }

    public LabelBuilder withStyle(UseFont type) {
        this.style = FontHelper.getStyleFont(type);
        return this;
    }

    public LabelBuilder withFontPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Set up a personalized style, if this method is used, the style provided by withStyle method
     * is not taken in count
     * @param path of the font file
     * @param color of the font
     * @param size of the font
     * @return this builder
     */
    public LabelBuilder withPersonalizedStyle(String path, Color color, int size) {
        this.path = path;
        this.color = color;
        this.size = size;
        this.style = null;
        return this;
    }

    // Label

    /**
     * Set up an alignement for the label
     * @param alignement please use Align for the attributes
     * @return this builder
     */
    public LabelBuilder withAlignement(int alignement) {
        this.alignement = alignement;
        return this;
    }

    public LabelBuilder isWrapped(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    // Main build method

    /**
     * Call this function in last position to get your label
     * @return a label with all the specified options
     */
    public Label build() {
        //final Label label = (Label) super.buildActor();

        TextButton.TextButtonStyle theStyle;
        if(style == null) {
            theStyle = Utils.getStyleFont(path, size, color);
        } else {
            theStyle = style;
        }
        Label l = new Label(text, new Label.LabelStyle(theStyle.font, theStyle.fontColor));

        // Actor Attributes

        l.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if (bw >= 0 && bh >= 0) {
            l.setBounds(bx, by, bw, by);
        }
        if (width >= 0) {
            l.setWidth(width);
        }
        if (height >= 0) {
            l.setHeight(height);
        }

        l.setVisible(visible);
        for (EventListener listener : this.eventListeners) {
            l.addListener(listener);
        }
        for (EventListener listener : this.captureListeners) {
            l.addCaptureListener(listener);
        }

        // Label Attributes

        l.setAlignment(alignement);
        l.setWrap(wrap);

        return l;
    }
}