package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Ketsuban
 */
public final class SignatureSpellbookChandra extends ExpansionSet {

    private static final SignatureSpellbookChandra instance = new SignatureSpellbookChandra();

    public static SignatureSpellbookChandra getInstance() {
        return instance;
    }

    private SignatureSpellbookChandra() {
        super("Signature Spellbook: Chandra", "SS3", ExpansionSet.buildDate(2020, 6, 26), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 1, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 2, Rarity.RARE, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Fiery Confluence", 3, Rarity.RARE, mage.cards.f.FieryConfluence.class));
        cards.add(new SetCardInfo("Past in Flames", 4, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Pyroblast", 5, Rarity.RARE, mage.cards.p.Pyroblast.class));
        cards.add(new SetCardInfo("Pyromancer Ascension", 6, Rarity.RARE, mage.cards.p.PyromancerAscension.class));
        cards.add(new SetCardInfo("Rite of Flame", 7, Rarity.RARE, mage.cards.r.RiteOfFlame.class));
        cards.add(new SetCardInfo("Young Pyromancer", 8, Rarity.RARE, mage.cards.y.YoungPyromancer.class));
    }
}
