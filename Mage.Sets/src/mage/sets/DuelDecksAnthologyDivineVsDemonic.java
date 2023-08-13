package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksAnthologyDivineVsDemonic extends ExpansionSet {

    private static final DuelDecksAnthologyDivineVsDemonic instance = new DuelDecksAnthologyDivineVsDemonic();

    public static DuelDecksAnthologyDivineVsDemonic getInstance() {
        return instance;
    }

    private DuelDecksAnthologyDivineVsDemonic() {
        super("Duel Decks: Anthology, Divine vs. Demonic", "DVD", ExpansionSet.buildDate(2014, 12, 5), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks: Anthology";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abyssal Gatekeeper", 31, Rarity.COMMON, mage.cards.a.AbyssalGatekeeper.class));
        cards.add(new SetCardInfo("Abyssal Specter", 40, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class));
        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 1, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfWrath.class));
        cards.add(new SetCardInfo("Angelic Benediction", 19, Rarity.UNCOMMON, mage.cards.a.AngelicBenediction.class));
        cards.add(new SetCardInfo("Angelic Page", 3, Rarity.COMMON, mage.cards.a.AngelicPage.class));
        cards.add(new SetCardInfo("Angelic Protector", 6, Rarity.UNCOMMON, mage.cards.a.AngelicProtector.class));
        cards.add(new SetCardInfo("Angel of Mercy", 9, Rarity.UNCOMMON, mage.cards.a.AngelOfMercy.class));
        cards.add(new SetCardInfo("Angel's Feather", 23, Rarity.UNCOMMON, mage.cards.a.AngelsFeather.class));
        cards.add(new SetCardInfo("Angelsong", 15, Rarity.COMMON, mage.cards.a.Angelsong.class));
        cards.add(new SetCardInfo("Barren Moor", 58, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Barter in Blood", 52, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Breeding Pit", 53, Rarity.UNCOMMON, mage.cards.b.BreedingPit.class));
        cards.add(new SetCardInfo("Cackling Imp", 41, Rarity.COMMON, mage.cards.c.CacklingImp.class));
        cards.add(new SetCardInfo("Charging Paladin", 4, Rarity.COMMON, mage.cards.c.ChargingPaladin.class));
        cards.add(new SetCardInfo("Consume Spirit", 56, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Corrupt", 55, Rarity.UNCOMMON, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Cruel Edict", 48, Rarity.UNCOMMON, mage.cards.c.CruelEdict.class));
        cards.add(new SetCardInfo("Daggerclaw Imp", 33, Rarity.UNCOMMON, mage.cards.d.DaggerclawImp.class));
        cards.add(new SetCardInfo("Dark Banishing", 50, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Dark Ritual", 45, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Demonic Tutor", 49, Rarity.UNCOMMON, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Demon's Horn", 57, Rarity.UNCOMMON, mage.cards.d.DemonsHorn.class));
        cards.add(new SetCardInfo("Demon's Jester", 38, Rarity.COMMON, mage.cards.d.DemonsJester.class));
        cards.add(new SetCardInfo("Duress", 46, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dusk Imp", 34, Rarity.COMMON, mage.cards.d.DuskImp.class));
        cards.add(new SetCardInfo("Faith's Fetters", 20, Rarity.COMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Fallen Angel", 42, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Foul Imp", 32, Rarity.COMMON, mage.cards.f.FoulImp.class));
        cards.add(new SetCardInfo("Healing Salve", 14, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Icatian Priest", 2, Rarity.UNCOMMON, mage.cards.i.IcatianPriest.class));
        cards.add(new SetCardInfo("Kuro, Pitlord", 44, Rarity.RARE, mage.cards.k.KuroPitlord.class));
        cards.add(new SetCardInfo("Lord of the Pit", 30, Rarity.MYTHIC, mage.cards.l.LordOfThePit.class));
        cards.add(new SetCardInfo("Luminous Angel", 12, Rarity.RARE, mage.cards.l.LuminousAngel.class));
        cards.add(new SetCardInfo("Marble Diamond", 24, Rarity.UNCOMMON, mage.cards.m.MarbleDiamond.class));
        cards.add(new SetCardInfo("Oni Possession", 51, Rarity.UNCOMMON, mage.cards.o.OniPossession.class));
        cards.add(new SetCardInfo("Otherworldly Journey", 16, Rarity.UNCOMMON, mage.cards.o.OtherworldlyJourney.class));
        cards.add(new SetCardInfo("Overeager Apprentice", 35, Rarity.COMMON, mage.cards.o.OvereagerApprentice.class));
        cards.add(new SetCardInfo("Pacifism", 17, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Plains", 26, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 27, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 28, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 29, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Promise of Power", 54, Rarity.RARE, mage.cards.p.PromiseOfPower.class));
        cards.add(new SetCardInfo("Reiver Demon", 43, Rarity.RARE, mage.cards.r.ReiverDemon.class));
        cards.add(new SetCardInfo("Reya Dawnbringer", 13, Rarity.RARE, mage.cards.r.ReyaDawnbringer.class));
        cards.add(new SetCardInfo("Righteous Cause", 22, Rarity.UNCOMMON, mage.cards.r.RighteousCause.class));
        cards.add(new SetCardInfo("Secluded Steppe", 25, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Serra Advocate", 7, Rarity.UNCOMMON, mage.cards.s.SerraAdvocate.class));
        cards.add(new SetCardInfo("Serra Angel", 10, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra's Boon", 18, Rarity.UNCOMMON, mage.cards.s.SerrasBoon.class));
        cards.add(new SetCardInfo("Serra's Embrace", 21, Rarity.UNCOMMON, mage.cards.s.SerrasEmbrace.class));
        cards.add(new SetCardInfo("Soot Imp", 37, Rarity.UNCOMMON, mage.cards.s.SootImp.class));
        cards.add(new SetCardInfo("Souldrinker", 39, Rarity.UNCOMMON, mage.cards.s.Souldrinker.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 36, Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Sustainer of the Realm", 8, Rarity.UNCOMMON, mage.cards.s.SustainerOfTheRealm.class));
        cards.add(new SetCardInfo("Swamp", 59, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 60, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 61, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 62, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Twilight Shepherd", 11, Rarity.RARE, mage.cards.t.TwilightShepherd.class));
        cards.add(new SetCardInfo("Unholy Strength", 47, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Venerable Monk", 5, Rarity.COMMON, mage.cards.v.VenerableMonk.class));
    }
}
