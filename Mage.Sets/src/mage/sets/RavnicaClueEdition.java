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
        cards.add(new SetCardInfo("Blood Crypt", 274, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Breeding Pool", 275, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Commander Mustard", 6, Rarity.RARE, mage.cards.c.CommanderMustard.class));
        cards.add(new SetCardInfo("Conclave Evangelist", 27, Rarity.RARE, mage.cards.c.ConclaveEvangelist.class));
        cards.add(new SetCardInfo("Dining Room", 15, Rarity.UNCOMMON, mage.cards.d.DiningRoom.class));
        cards.add(new SetCardInfo("Ecstatic Electromancer", 31, Rarity.UNCOMMON, mage.cards.e.EcstaticElectromancer.class));
        cards.add(new SetCardInfo("Godless Shrine", 276, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Knife", 10, Rarity.UNCOMMON, mage.cards.k.Knife.class));
        cards.add(new SetCardInfo("Lavinia, Foil to Conspiracy", 36, Rarity.RARE, mage.cards.l.LaviniaFoilToConspiracy.class));
        cards.add(new SetCardInfo("Lead Pipe", 9, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Library", 18, Rarity.UNCOMMON, mage.cards.l.Library.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 278, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Sacred Foundry", 279, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Senator Peacock", 2, Rarity.RARE, mage.cards.s.SenatorPeacock.class));
        cards.add(new SetCardInfo("Steam Vents", 280, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stomping Ground", 281, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Study", 21, Rarity.UNCOMMON, mage.cards.s.Study.class));
        cards.add(new SetCardInfo("Temple Garden", 282, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Undercover Butler", 49, Rarity.UNCOMMON, mage.cards.u.UndercoverButler.class));
        cards.add(new SetCardInfo("Vernal Sovereign", 51, Rarity.RARE, mage.cards.v.VernalSovereign.class));
        cards.add(new SetCardInfo("Watery Grave", 283, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
