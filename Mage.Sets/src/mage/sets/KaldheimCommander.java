package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class KaldheimCommander extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList(
            "Cosmic Intervention",
            "Ethereal Valkyrie",
            "Ranar the Ever-Watchful",
            "Sage of the Beyond",
            "Spectral Deluge",
            "Stoic Farmer",
            "Tales of the Ancestors"
    );

    private static final KaldheimCommander instance = new KaldheimCommander();

    public static KaldheimCommander getInstance() {
        return instance;
    }

    private KaldheimCommander() {
        super("Kaldheim Commander", "KHC", ExpansionSet.buildDate(2021, 2, 5), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Crown of Skemfar", 13, Rarity.RARE, mage.cards.c.CrownOfSkemfar.class));
        cards.add(new SetCardInfo("Elderfang Venom", 15, Rarity.RARE, mage.cards.e.ElderfangVenom.class));
        cards.add(new SetCardInfo("Inspired Sphinx", 40, Rarity.MYTHIC, mage.cards.i.InspiredSphinx.class));
        cards.add(new SetCardInfo("Lathril, Blade of the Elves", 1, Rarity.MYTHIC, mage.cards.l.LathrilBladeOfTheElves.class));
        cards.add(new SetCardInfo("Ruthless Winnower", 10, Rarity.RARE, mage.cards.r.RuthlessWinnower.class));
        cards.add(new SetCardInfo("Spectral Deluge", 7, Rarity.RARE, mage.cards.s.SpectralDeluge.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
