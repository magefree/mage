package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ps15
 */
public class SanDiegoComicCon2015 extends ExpansionSet {

    private static final SanDiegoComicCon2015 instance = new SanDiegoComicCon2015();

    public static SanDiegoComicCon2015 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2015() {
        super("San Diego Comic-Con 2015", "PS15", ExpansionSet.buildDate(2015, 7, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", 135, Rarity.MYTHIC, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Chandra, Roaring Flame", 135, Rarity.MYTHIC, mage.cards.c.ChandraRoaringFlame.class));
        cards.add(new SetCardInfo("Gideon, Battle-Forged", 23, Rarity.MYTHIC, mage.cards.g.GideonBattleForged.class));
        cards.add(new SetCardInfo("Jace, Telepath Unbound", 60, Rarity.MYTHIC, mage.cards.j.JaceTelepathUnbound.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", 60, Rarity.MYTHIC, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", 23, Rarity.MYTHIC, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", 106, Rarity.MYTHIC, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", 106, Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", 189, Rarity.MYTHIC, mage.cards.n.NissaVastwoodSeer.class));
        cards.add(new SetCardInfo("Nissa, Sage Animist", 189, Rarity.MYTHIC, mage.cards.n.NissaSageAnimist.class));
    }
}
