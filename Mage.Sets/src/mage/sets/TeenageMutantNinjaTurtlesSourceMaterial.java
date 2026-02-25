package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TeenageMutantNinjaTurtlesSourceMaterial extends ExpansionSet {

    private static final TeenageMutantNinjaTurtlesSourceMaterial instance = new TeenageMutantNinjaTurtlesSourceMaterial();

    public static TeenageMutantNinjaTurtlesSourceMaterial getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtlesSourceMaterial() {
        super("Teenage Mutant Ninja Turtles Source Material", "PZA", ExpansionSet.buildDate(2026, 3, 6), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 64; // TODO: Update once more info is available

        cards.add(new SetCardInfo("All Will Be One", 8, Rarity.MYTHIC, mage.cards.a.AllWillBeOne.class));
        cards.add(new SetCardInfo("Arcbound Ravager", 14, Rarity.MYTHIC, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Ashcoat of the Shadow Swarm", 6, Rarity.MYTHIC, mage.cards.a.AshcoatOfTheShadowSwarm.class));
        cards.add(new SetCardInfo("Brainstorm", 4, Rarity.MYTHIC, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Conqueror's Flail", 15, Rarity.MYTHIC, mage.cards.c.ConquerorsFlail.class));
        cards.add(new SetCardInfo("Cytoplast Manipulator", 5, Rarity.MYTHIC, mage.cards.c.CytoplastManipulator.class));
        cards.add(new SetCardInfo("Doubling Season", 11, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Metallic Mimic", 16, Rarity.MYTHIC, mage.cards.m.MetallicMimic.class));
        cards.add(new SetCardInfo("Path to Exile", 1, Rarity.MYTHIC, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Plague of Vermin", 7, Rarity.MYTHIC, mage.cards.p.PlagueOfVermin.class));
        cards.add(new SetCardInfo("Rhythm of the Wild", 12, Rarity.MYTHIC, mage.cards.r.RhythmOfTheWild.class));
        cards.add(new SetCardInfo("Shadowspear", 17, Rarity.MYTHIC, mage.cards.s.Shadowspear.class));
        cards.add(new SetCardInfo("Silverclad Ferocidons", 9, Rarity.MYTHIC, mage.cards.s.SilvercladFerocidons.class));
        cards.add(new SetCardInfo("Sword of Sinew and Steel", 18, Rarity.MYTHIC, mage.cards.s.SwordOfSinewAndSteel.class));
        cards.add(new SetCardInfo("Teleportation Circle", 2, Rarity.MYTHIC, mage.cards.t.TeleportationCircle.class));
        cards.add(new SetCardInfo("Trouble in Pairs", 3, Rarity.MYTHIC, mage.cards.t.TroubleInPairs.class));
        cards.add(new SetCardInfo("Umezawa's Jitte", 19, Rarity.MYTHIC, mage.cards.u.UmezawasJitte.class));
        cards.add(new SetCardInfo("Undercity Sewers", 20, Rarity.MYTHIC, mage.cards.u.UndercitySewers.class));
        cards.add(new SetCardInfo("Underworld Breach", 10, Rarity.MYTHIC, mage.cards.u.UnderworldBreach.class));
        cards.add(new SetCardInfo("Waves of Aggression", 13, Rarity.MYTHIC, mage.cards.w.WavesOfAggression.class));
    }
}
