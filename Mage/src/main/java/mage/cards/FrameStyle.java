/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards;

/**
 * @author StravantUser
 */
public enum FrameStyle {
    /**
     * The default card frame, normal M15 card frames
     */
    M15_NORMAL(BorderType.M15, false),
    /**
     * Battle for Zendikar full art basic lands
     */
    BFZ_FULL_ART_BASIC(BorderType.M15, true),
    /**
     * Kaladesh block Inventions
     */
    KLD_INVENTION(BorderType.M15, false),
    /**
     * Zendikar full art lands
     */
    ZEN_FULL_ART_BASIC(BorderType.MOD, true),
    /**
     * Magic Player Rewards full art cards
     */
    MPRP_FULL_ART_BASIC(BorderType.MOD, true),
    /**
     * Magic Player Online Promos full art cards
     */
    MPOP_FULL_ART_BASIC(BorderType.MOD, true),
    /**
     * Unhinged full art lands
     */
    UNH_FULL_ART_BASIC(BorderType.SPC, true),
    /**
     * Unglued full art lands
     */
    UGL_FULL_ART_BASIC(BorderType.SPC, true),
    /**
     * Unstable full art lands
     */
    UST_FULL_ART_BASIC(BorderType.SPC, true);


    /**
     * General type of card
     */
    public enum BorderType {
        /**
         * Various specialty borders EG: Unhinged, Unglued
         */
        SPC,
        /**
         * Old border cards
         */
        OLD,
        /**
         * Modern border cards (8th -> Theros)
         */
        MOD,
        /**
         * M15 border cards (M14 -> current)
         */
        M15
    }

    private final BorderType borderType;
    private final boolean isFullArt;

    public BorderType getBorderType() {
        return borderType;
    }

    public boolean isFullArt() {
        return isFullArt;
    }

    FrameStyle(BorderType borderType, boolean isFullArt) {
        this.borderType = borderType;
        this.isFullArt = isFullArt;
    }
}
