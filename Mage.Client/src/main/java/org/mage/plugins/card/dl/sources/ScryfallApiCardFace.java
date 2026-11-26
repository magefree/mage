package org.mage.plugins.card.dl.sources;

import java.util.Map;

/**
 * Scryfall API: card face info (used for double faced cards)
 * <p>
 * API docs: <a href="https://scryfall.com/docs/api/cards">here</a>
 * <p>
 *
 * @author JayDi85
 */
public class ScryfallApiCardFace {
    public String name;
    public String layout;
    public Map<String, String> image_uris;

    // fast access fields, fills on loading
    transient public String imageSmall = "";
    transient public String imageNormal = "";
    transient public String imageLarge = "";

    public void prepareCompatibleData() {
        if (this.image_uris != null) {
            this.imageSmall = this.image_uris.getOrDefault("thumb", "");
            this.imageNormal = this.image_uris.getOrDefault("grid", "");
            this.imageLarge = this.image_uris.getOrDefault("display", "");
            this.image_uris = null;
        }
    }

    public String findImage(String imageSize) {
        // api possible values:
        // - small (deprecated)
    	// - thumb
        // - normal (deprecated)
    	// - grid
        // - large (deprecated)
    	// - display
        // - png
        // - art_crop
        // - border_crop
        switch (imageSize) {
            case "small":
            case "thumb":
                return this.imageSmall;
            case "normal":
            case "grid":
                return this.imageNormal;
            case "large":
            case "display":
                return this.imageLarge;
            default:
                throw new IllegalArgumentException("Unsupported image size: " + imageSize);
        }
    }
}
