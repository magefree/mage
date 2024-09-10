package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slp
 */
public class SecretLairShowdown extends ExpansionSet {

    private static final SecretLairShowdown instance = new SecretLairShowdown();

    public static SecretLairShowdown getInstance() {
        return instance;
    }

    private SecretLairShowdown() {
        super("Secret Lair Showdown", "SLP", ExpansionSet.buildDate(2023, 2, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
		
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 7, Rarity.RARE, mage.cards.a.AnOfferYouCantRefuse.class));
        cards.add(new SetCardInfo("Brainstorm", 1, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Dark Ritual", 16, Rarity.RARE, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Death's Shadow", 8, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Dragonlord Silumgar", 9, Rarity.MYTHIC, mage.cards.d.DragonlordSilumgar.class));
        cards.add(new SetCardInfo("Eldritch Evolution", 5, Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Explore", 12, Rarity.RARE, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Expressive Iteration", 13, Rarity.RARE, mage.cards.e.ExpressiveIteration.class));
        cards.add(new SetCardInfo("Fatal Push", 3, Rarity.RARE, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Goblin Guide", 23, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Murktide Regent", 17, Rarity.MYTHIC, mage.cards.m.MurktideRegent.class));
        cards.add(new SetCardInfo("Ponder", 19, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 2, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class));
        cards.add(new SetCardInfo("Relentless Rats", 10, Rarity.RARE, mage.cards.r.RelentlessRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relentless Rats", 11, Rarity.RARE, mage.cards.r.RelentlessRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 24, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Spell Pierce", 18, Rarity.RARE, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Springleaf Drum", 22, Rarity.RARE, mage.cards.s.SpringleafDrum.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 6, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Unholy Heat", 4, Rarity.RARE, mage.cards.u.UnholyHeat.class));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", 14, Rarity.RARE, mage.cards.v.ValakutTheMoltenPinnacle.class));
        cards.add(new SetCardInfo("Wrenn and Six", 15, Rarity.MYTHIC, mage.cards.w.WrennAndSix.class));
    }
}
