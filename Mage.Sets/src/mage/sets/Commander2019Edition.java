package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2019Edition extends ExpansionSet {

    private static final Commander2019Edition instance = new Commander2019Edition();

    public static Commander2019Edition getInstance() {
        return instance;
    }

    private Commander2019Edition() {
        super("Commander 2019 Edition", "C19", ExpansionSet.buildDate(2019, 8, 23), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Anje Falkenrath", 37, Rarity.MYTHIC, mage.cards.a.AnjeFalkenrath.class));
        cards.add(new SetCardInfo("Anje's Ravager", 22, Rarity.RARE, mage.cards.a.AnjesRavager.class));
        cards.add(new SetCardInfo("Apex Altisaur", 31, Rarity.RARE, mage.cards.a.ApexAltisaur.class));
        cards.add(new SetCardInfo("Bane of the Living", 104, Rarity.RARE, mage.cards.b.BaneOfTheLiving.class));
        cards.add(new SetCardInfo("Chromeshell Crab", 81, Rarity.RARE, mage.cards.c.ChromeshellCrab.class));
        cards.add(new SetCardInfo("Cinder Glade", 236, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Deathmist Raptor", 160, Rarity.MYTHIC, mage.cards.d.DeathmistRaptor.class));
        cards.add(new SetCardInfo("Den Protector", 161, Rarity.RARE, mage.cards.d.DenProtector.class));
        cards.add(new SetCardInfo("Farseek", 165, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Forest", 300, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gerrard, Weatherlight Hero", 41, Rarity.RARE, mage.cards.g.GerrardWeatherlightHero.class));
        cards.add(new SetCardInfo("Ghired's Belligerence", 25, Rarity.RARE, mage.cards.g.GhiredsBelligerence.class));
        cards.add(new SetCardInfo("Ghired, Conclave Exile", 42, Rarity.MYTHIC, mage.cards.g.GhiredConclaveExile.class));
        cards.add(new SetCardInfo("Great Oak Guardian", 170, Rarity.UNCOMMON, mage.cards.g.GreatOakGuardian.class));
        cards.add(new SetCardInfo("Grismold, the Dreadsower", 44, Rarity.RARE, mage.cards.g.GrismoldTheDreadsower.class));
        cards.add(new SetCardInfo("Hooded Hydra", 172, Rarity.MYTHIC, mage.cards.h.HoodedHydra.class));
        cards.add(new SetCardInfo("Island", 291, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ixidron", 87, Rarity.RARE, mage.cards.i.Ixidron.class));
        cards.add(new SetCardInfo("Kadena's Silencer", 8, Rarity.RARE, mage.cards.k.KadenasSilencer.class));
        cards.add(new SetCardInfo("Kadena, Slinking Sorcerer", 45, Rarity.MYTHIC, mage.cards.k.KadenaSlinkingSorcerer.class));
        cards.add(new SetCardInfo("Kheru Spellsnatcher", 89, Rarity.RARE, mage.cards.k.KheruSpellsnatcher.class));
        cards.add(new SetCardInfo("Leadership Vacuum", 9, Rarity.UNCOMMON, mage.cards.l.LeadershipVacuum.class));
        cards.add(new SetCardInfo("Mountain", 297, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nantuko Vigilante", 174, Rarity.COMMON, mage.cards.n.NantukoVigilante.class));
        cards.add(new SetCardInfo("Plains", 288, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prairie Stream", 265, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Prismatic Strands", 69, Rarity.COMMON, mage.cards.p.PrismaticStrands.class));
        cards.add(new SetCardInfo("Reality Shift", 92, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("River Kelpie", 93, Rarity.RARE, mage.cards.r.RiverKelpie.class));
        cards.add(new SetCardInfo("Sagu Mauler", 200, Rarity.RARE, mage.cards.s.SaguMauler.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 177, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Sanctum of Eternity", 59, Rarity.RARE, mage.cards.s.SanctumOfEternity.class));
        cards.add(new SetCardInfo("Scaretiller", 57, Rarity.COMMON, mage.cards.s.Scaretiller.class));
        cards.add(new SetCardInfo("Seedborn Muse", 179, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 5, Rarity.RARE, mage.cards.s.SevinnesReclamation.class));
        cards.add(new SetCardInfo("Sevinne, the Chronoclasm", 49, Rarity.MYTHIC, mage.cards.s.SevinneTheChronoclasm.class));
        cards.add(new SetCardInfo("Stratus Dancer", 96, Rarity.RARE, mage.cards.s.StratusDancer.class));
        cards.add(new SetCardInfo("Strionic Resonator", 224, Rarity.RARE, mage.cards.s.StrionicResonator.class));
        cards.add(new SetCardInfo("Sungrass Prairie", 277, Rarity.RARE, mage.cards.s.SungrassPrairie.class));
        cards.add(new SetCardInfo("Sunken Hollow", 278, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Swamp", 294, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tempt with Discovery", 183, Rarity.RARE, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Thalia's Geistcaller", 7, Rarity.RARE, mage.cards.t.ThaliasGeistcaller.class));
        cards.add(new SetCardInfo("Thespian's Stage", 282, Rarity.RARE, mage.cards.t.ThespiansStage.class));
        cards.add(new SetCardInfo("Thran Dynamo", 223, Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Trail of Mystery", 186, Rarity.RARE, mage.cards.t.TrailOfMystery.class));
        cards.add(new SetCardInfo("Vesuvan Shapeshifter", 101, Rarity.RARE, mage.cards.v.VesuvanShapeshifter.class));
        cards.add(new SetCardInfo("Vraska the Unseen", 207, Rarity.MYTHIC, mage.cards.v.VraskaTheUnseen.class));
    }
}
