package org.mage.plugins.card.dl.sources;

import mage.cards.decks.CardNameUtil;

import java.util.List;
import java.util.Map;

/**
 * Scryfall API: card info
 * <p>
 * API docs: <a href="https://scryfall.com/docs/api/cards">here</a>
 * <p>
 * Field values depends on data source, check it on null (as example: bulk data from oracle cards and bulk data from all cards)
 *
 * @author JayDi85
 */
public class ScryfallApiCard {

    public String id;
    public String name;
    public String set; // set code
    public String collector_number;
    public String lang;
    public String layout; // card type like adventure, etc, see https://scryfall.com/docs/api/layouts
    public List<ScryfallApiCardFace> card_faces;
    public String image_status; // missing, placeholder, lowres, or highres_scan
    public Map<String, String> image_uris;

    // fast access fields, fills on loading
    transient public String imageSmall = "";
    transient public String imageNormal = "";
    transient public String imageLarge = "";

    // potentially interesting fields, can be used in other places
    //public UUID oracle_id; // TODO: implement card hint with oracle/cr ruling texts (see Rulings bulk data)
    //public Integer edhrec_rank; // TODO: use it to rating cards for AI and draft bots
    //public Object legalities; // TODO: add verify check for bans list
    //public Boolean full_art; // TODO: add verify check for full art usage in sets
    //public Object prices; // TODO: add total deck price and table limit by deck's price
    //public Date released_at; // the date this card was first released.
    //public String watermark; // background watermark image for some cards

    public void prepareCompatibleData() {
        // take images from main card
        if (this.image_uris != null) {
            this.imageSmall = this.image_uris.getOrDefault("small", "");
            this.imageNormal = this.image_uris.getOrDefault("normal", "");
            this.imageLarge = this.image_uris.getOrDefault("large", "");
            this.image_uris = null;
        }

        // take first available images from one of the faces
        if (this.card_faces != null) {
            this.card_faces.forEach(ScryfallApiCardFace::prepareCompatibleData);
        }

        // workaround for adventure/omen card name fix:
        // - scryfall: Ondu Knotmaster // Throw a Line
        // - xmage: Ondu Knotmaster
        if (this.layout.equals("adventure")) {
            this.name = this.card_faces.get(0).name;
        }

        // workaround for flip card name and image fixes:
        // - scryfall: Budoka Pupil // Ichiga, Who Topples Oaks
        // - xmage: Budoka Pupil
        if (this.layout.equals("flip")) {
            if (!this.card_faces.get(0).imageNormal.isEmpty()
                    || !this.card_faces.get(1).imageNormal.isEmpty()) {
                throw new IllegalArgumentException("Scryfall: unsupported data type, flip parts must not have images data in scryfall "
                        + this.set + " - " + this.collector_number + " - " + this.name);
            }

            // fix name
            this.name = this.card_faces.get(0).name;

            // fix image (xmage uses diff cards for flips, but it's same image)
            // example: https://scryfall.com/card/sok/103/homura-human-ascendant-homuras-essence
            this.card_faces.get(0).image_uris = this.image_uris;
            this.card_faces.get(0).imageSmall = this.imageSmall;
            this.card_faces.get(0).imageNormal = this.imageNormal;
            this.card_faces.get(0).imageLarge = this.imageLarge;
            this.card_faces.get(1).image_uris = this.image_uris;
            this.card_faces.get(1).imageSmall = this.imageSmall;
            this.card_faces.get(1).imageNormal = this.imageNormal;
            this.card_faces.get(1).imageLarge = this.imageLarge;
        }

        // workaround for reversed cards:
        // - scryfall: Command Tower // Command Tower
        // - xmage: Command Tower (second side as diff card and direct link image), example: https://scryfall.com/card/rex/26/command-tower-command-tower
        if (this.layout.equals("reversible_card")) {
            if (false) {
                // ignore
            } else if (this.card_faces == null) {
                // TODO: temporary fix, delete after scryfall site update
                // broken adventure/omen card (scryfall changed it for some reason)
                // Scavenger Regent // Exude Toxin
                // https://scryfall.com/card/tdm/90/scavenger-regent-exude-toxin
                if (this.name.contains("//")) {
                    throw new IllegalArgumentException("Scryfall: unsupported data type, broken reversible_card must have same simple name"
                            + this.set + " - " + this.collector_number + " - " + this.name);
                }
            } else if (this.card_faces.get(0).layout.equals("reversible_card")) {
                // TODO: temporary fix, delete after scryfall site update
                // broken adventure/omen card (scryfall changed it for some reason)
                // Bloomvine Regent // Claim Territory
                // https://scryfall.com/card/tdm/381/bloomvine-regent-claim-territory-bloomvine-regent
                this.name = this.card_faces.get(0).name;
            } else if (this.card_faces.get(0).layout == null || this.card_faces.get(0).layout.equals("normal")) {
                // simple card
                // Command Tower // Command Tower
                // https://scryfall.com/card/rex/26/command-tower-command-tower
                if (!this.card_faces.get(0).name.equals(this.card_faces.get(1).name)) {
                    throw new IllegalArgumentException("Scryfall: unsupported data type, normal reversible_card must have same name in faces"
                            + this.set + " - " + this.collector_number + " - " + this.name);
                }
                this.name = this.card_faces.get(0).name;
            } else if (this.card_faces.get(0).layout.equals("adventure")) {
                // adventure/omen card
                // Bloomvine Regent // Claim Territory
                // https://scryfall.com/card/tdm/381/bloomvine-regent-claim-territory-bloomvine-regent
                this.name = this.card_faces.get(0).name;
                if (this.card_faces.get(0).name.equals(this.card_faces.get(1).name)) {
                    throw new IllegalArgumentException("Scryfall: unsupported data type, adventure/omen's reversible_card must have diff names in faces "
                            + this.set + " - " + this.collector_number + " - " + this.name);
                }
            } else if (this.card_faces.get(0).layout.equals("token")) {
                // token (it's not used for xmage's tokens, but must pass checks here anyway)
                // Mechtitan // Mechtitan
                // https://scryfall.com/card/sld/1969/mechtitan-mechtitan
                this.name = this.card_faces.get(0).name;
            } else {
                throw new IllegalArgumentException("Scryfall: unsupported layout type in reversible_card - "
                        + this.card_faces.get(0).layout + " - " + this.set + " - " + this.collector_number + " - " + this.name);
            }
        }

        // workaround for non ascii names
        // - scryfall uses original names like Arna Kennerüd, Skycaptain
        // - xmage need ascii only names like Arna Kennerud, Skycaptain
        this.name = CardNameUtil.normalizeCardName(this.name);

        // workaround for non scii card numbers
        // - scryfall uses unicode numbers for reprints like Chandra Nalaar - dd2 - 34★ https://scryfall.com/card/dd2/34%E2%98%85/
        // - xmage uses ascii alternative Chandra Nalaar - dd2 - 34*
        this.collector_number = transformCardNumberFromScryfallToXmage(this.collector_number);
    }

    public static String transformCardNumberFromXmageToScryfall(String cardNumber) {
        String res = cardNumber;
        if (res.endsWith("*")) {
            res = res.substring(0, res.length() - 1) + "★";
        }
        if (res.endsWith("+")) {
            res = res.substring(0, res.length() - 1) + "†";
        }
        if (res.endsWith("Ph")) {
            res = res.substring(0, res.length() - 2) + "Φ";
        }
        return res;
    }

    public static String transformCardNumberFromScryfallToXmage(String cardNumber) {
        String res = cardNumber;
        if (res.endsWith("★")) {
            res = res.substring(0, res.length() - 1) + "*";
        }
        if (res.endsWith("†")) {
            res = res.substring(0, res.length() - 1) + "+";
        }
        if (res.endsWith("Φ")) {
            res = res.substring(0, res.length() - 1) + "Ph";
        }
        return res;
    }

    public String findImage(String imageSize) {
        // api possible values:
        // - small
        // - normal
        // - large
        // - png
        // - art_crop
        // - border_crop
        switch (imageSize) {
            case "small":
                return this.imageSmall;
            case "normal":
                return this.imageNormal;
            case "large":
                return this.imageLarge;
            default:
                throw new IllegalArgumentException("Unsupported image size: " + imageSize);
        }
    }
}
