package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p30a
 *
 * @author Jmlundeen
 */
public class EternalWeekend extends ExpansionSet {

    private static final EternalWeekend instance = new EternalWeekend();

    public static EternalWeekend getInstance() {
        return instance;
    }

    private EternalWeekend() {
        super("Eternal Weekend", "PEWK", ExpansionSet.buildDate(2022, 11, 26), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Crop Rotation", "2024b", Rarity.RARE, mage.cards.c.CropRotation.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon's Rage Channeler", "2023b", Rarity.RARE, mage.cards.d.DragonsRageChanneler.class, RETRO_ART));
        cards.add(new SetCardInfo("Gush", "2022a", Rarity.RARE, mage.cards.g.Gush.class, RETRO_ART));
        cards.add(new SetCardInfo("Mental Misstep", "2023a", Rarity.RARE, mage.cards.m.MentalMisstep.class, RETRO_ART));
        cards.add(new SetCardInfo("Ponder", "2022b", Rarity.RARE, mage.cards.p.Ponder.class, RETRO_ART));
        cards.add(new SetCardInfo("Tendrils of Agony", "2025a", Rarity.RARE, mage.cards.t.TendrilsOfAgony.class, RETRO_ART));
        cards.add(new SetCardInfo("Tinker", "2024a", Rarity.MYTHIC, mage.cards.t.Tinker.class, RETRO_ART));
        cards.add(new SetCardInfo("Trinisphere", "2025b", Rarity.MYTHIC, mage.cards.t.Trinisphere.class, RETRO_ART));
    }
}
