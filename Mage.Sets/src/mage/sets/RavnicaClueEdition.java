package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class RavnicaClueEdition extends ExpansionSet {

    private static final RavnicaClueEdition instance = new RavnicaClueEdition();

    public static RavnicaClueEdition getInstance() {
        return instance;
    }

    private RavnicaClueEdition() {
        super("Ravnica: Clue Edition", "CLU", ExpansionSet.buildDate(2024, 2, 9), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Afterlife Insurance", 23, Rarity.UNCOMMON, mage.cards.a.AfterlifeInsurance.class));
        cards.add(new SetCardInfo("Ballroom", 12, Rarity.UNCOMMON, mage.cards.b.Ballroom.class));
        cards.add(new SetCardInfo("Billiard Room", 13, Rarity.UNCOMMON, mage.cards.b.BilliardRoom.class));
        cards.add(new SetCardInfo("Blood Crypt", 274, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Breeding Pool", 275, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Candlestick", 8, Rarity.UNCOMMON, mage.cards.c.Candlestick.class));
        cards.add(new SetCardInfo("Carnage Interpreter", 26, Rarity.RARE, mage.cards.c.CarnageInterpreter.class));
        cards.add(new SetCardInfo("Commander Mustard", 6, Rarity.RARE, mage.cards.c.CommanderMustard.class));
        cards.add(new SetCardInfo("Conclave Evangelist", 27, Rarity.RARE, mage.cards.c.ConclaveEvangelist.class));
        cards.add(new SetCardInfo("Conservatory", 14, Rarity.UNCOMMON, mage.cards.c.Conservatory.class));
        cards.add(new SetCardInfo("Dining Room", 15, Rarity.UNCOMMON, mage.cards.d.DiningRoom.class));
        cards.add(new SetCardInfo("Ecstatic Electromancer", 31, Rarity.UNCOMMON, mage.cards.e.EcstaticElectromancer.class));
        cards.add(new SetCardInfo("Godless Shrine", 276, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Hall", 16, Rarity.UNCOMMON, mage.cards.h.Hall.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Kitchen", 17, Rarity.UNCOMMON, mage.cards.k.Kitchen.class));
        cards.add(new SetCardInfo("Knife", 10, Rarity.UNCOMMON, mage.cards.k.Knife.class));
        cards.add(new SetCardInfo("Lavinia, Foil to Conspiracy", 36, Rarity.RARE, mage.cards.l.LaviniaFoilToConspiracy.class));
        cards.add(new SetCardInfo("Lead Pipe", 9, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Library", 18, Rarity.UNCOMMON, mage.cards.l.Library.class));
        cards.add(new SetCardInfo("Lounge", 19, Rarity.UNCOMMON, mage.cards.l.Lounge.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 278, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Sacred Foundry", 279, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Secret Passage", 20, Rarity.UNCOMMON, mage.cards.s.SecretPassage.class));
        cards.add(new SetCardInfo("Senator Peacock", 2, Rarity.RARE, mage.cards.s.SenatorPeacock.class));
        cards.add(new SetCardInfo("Sludge Titan", 43, Rarity.RARE, mage.cards.s.SludgeTitan.class));
        cards.add(new SetCardInfo("Steam Vents", 280, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stomping Ground", 281, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Study", 21, Rarity.UNCOMMON, mage.cards.s.Study.class));
        cards.add(new SetCardInfo("Syndicate Heavy", 47, Rarity.RARE, mage.cards.s.SyndicateHeavy.class));
        cards.add(new SetCardInfo("Temple Garden", 282, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Undercover Butler", 49, Rarity.UNCOMMON, mage.cards.u.UndercoverButler.class));
        cards.add(new SetCardInfo("Vernal Sovereign", 51, Rarity.RARE, mage.cards.v.VernalSovereign.class));
        cards.add(new SetCardInfo("Watery Grave", 283, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
