package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class GameNight extends ExpansionSet {

    private static final GameNight instance = new GameNight();

    public static GameNight getInstance() {
        return instance;
    }

    private GameNight() {
        super("Game Night", "GNT", ExpansionSet.buildDate(2018, 11, 16), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Advanced Stitchwing", 18, Rarity.UNCOMMON, mage.cards.a.AdvancedStitchwing.class));
        cards.add(new SetCardInfo("Aerial Responder", 6, Rarity.UNCOMMON, mage.cards.a.AerialResponder.class));
        cards.add(new SetCardInfo("Air Elemental", 19, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Always Watching", 7, Rarity.RARE, mage.cards.a.AlwaysWatching.class));
        cards.add(new SetCardInfo("Avatar of Growth", 5, Rarity.MYTHIC, mage.cards.a.AvatarOfGrowth.class));
        cards.add(new SetCardInfo("Benalish Marshal", 8, Rarity.RARE, mage.cards.b.BenalishMarshal.class));
        cards.add(new SetCardInfo("Bombard", 37, Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Bone Splinters", 27, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bright Reprisal", 9, Rarity.UNCOMMON, mage.cards.b.BrightReprisal.class));
        cards.add(new SetCardInfo("Call the Cavalry", 10, Rarity.COMMON, mage.cards.c.CallTheCavalry.class));
        cards.add(new SetCardInfo("Captivating Crew", 38, Rarity.RARE, mage.cards.c.CaptivatingCrew.class));
        cards.add(new SetCardInfo("Claustrophobia", 20, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Cruel Revival", 28, Rarity.UNCOMMON, mage.cards.c.CruelRevival.class));
        cards.add(new SetCardInfo("Dragon Fodder", 39, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Eager Construct", 51, Rarity.COMMON, mage.cards.e.EagerConstruct.class));
        cards.add(new SetCardInfo("Everdawn Champion", 11, Rarity.UNCOMMON, mage.cards.e.EverdawnChampion.class));
        cards.add(new SetCardInfo("Fan Bearer", 12, Rarity.COMMON, mage.cards.f.FanBearer.class));
        cards.add(new SetCardInfo("Favorable Winds", 21, Rarity.UNCOMMON, mage.cards.f.FavorableWinds.class));
        cards.add(new SetCardInfo("Filigree Familiar", 52, Rarity.UNCOMMON, mage.cards.f.FiligreeFamiliar.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 29, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Forest", 67, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 68, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 44, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Goblin Goliath", 4, Rarity.MYTHIC, mage.cards.g.GoblinGoliath.class));
        cards.add(new SetCardInfo("Gruesome Fate", 30, Rarity.COMMON, mage.cards.g.GruesomeFate.class));
        cards.add(new SetCardInfo("Howling Golem", 53, Rarity.UNCOMMON, mage.cards.h.HowlingGolem.class));
        cards.add(new SetCardInfo("Hydrolash", 22, Rarity.UNCOMMON, mage.cards.h.Hydrolash.class));
        cards.add(new SetCardInfo("Inspired Charge", 13, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Inspired Sphinx", 2, Rarity.MYTHIC, mage.cards.i.InspiredSphinx.class));
        cards.add(new SetCardInfo("Inspiring Captain", 14, Rarity.COMMON, mage.cards.i.InspiringCaptain.class));
        cards.add(new SetCardInfo("Island", 61, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 62, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Delver", 45, Rarity.COMMON, mage.cards.j.JungleDelver.class));
        cards.add(new SetCardInfo("Languish", 31, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Liliana's Mastery", 32, Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Llanowar Elves", 46, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 33, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Manalith", 54, Rarity.COMMON, mage.cards.m.Manalith.class));
        cards.add(new SetCardInfo("Mesa Unicorn", 15, Rarity.COMMON, mage.cards.m.MesaUnicorn.class));
        cards.add(new SetCardInfo("Militant Angel", 1, Rarity.MYTHIC, mage.cards.m.MilitantAngel.class));
        cards.add(new SetCardInfo("Mountain", 65, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 66, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa's Revelation", 47, Rarity.RARE, mage.cards.n.NissasRevelation.class));
        cards.add(new SetCardInfo("Overcome", 48, Rarity.UNCOMMON, mage.cards.o.Overcome.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 55, Rarity.UNCOMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Plains", 59, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 60, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckless Scholar", 23, Rarity.UNCOMMON, mage.cards.r.RecklessScholar.class));
        cards.add(new SetCardInfo("Rhonas's Monument", 56, Rarity.UNCOMMON, mage.cards.r.RhonassMonument.class));
        cards.add(new SetCardInfo("Rolling Thunder", 40, Rarity.UNCOMMON, mage.cards.r.RollingThunder.class));
        cards.add(new SetCardInfo("Rot Hulk", 3, Rarity.MYTHIC, mage.cards.r.RotHulk.class));
        cards.add(new SetCardInfo("Seek the Wilds", 49, Rarity.COMMON, mage.cards.s.SeekTheWilds.class));
        cards.add(new SetCardInfo("Seismic Elemental", 41, Rarity.UNCOMMON, mage.cards.s.SeismicElemental.class));
        cards.add(new SetCardInfo("Serra Angel", 16, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 42, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Snare Thopter", 57, Rarity.UNCOMMON, mage.cards.s.SnareThopter.class));
        cards.add(new SetCardInfo("Soulblade Djinn", 24, Rarity.RARE, mage.cards.s.SoulbladeDjinn.class));
        cards.add(new SetCardInfo("Subjugator Angel", 17, Rarity.UNCOMMON, mage.cards.s.SubjugatorAngel.class));
        cards.add(new SetCardInfo("Swamp", 63, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 64, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tattered Mummy", 34, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Thallid Soothsayer", 35, Rarity.UNCOMMON, mage.cards.t.ThallidSoothsayer.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 50, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Tormenting Voice", 43, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Welder Automaton", 58, Rarity.COMMON, mage.cards.w.WelderAutomaton.class));
        cards.add(new SetCardInfo("Whirler Rogue", 25, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Zahid, Djinn of the Lamp", 26, Rarity.RARE, mage.cards.z.ZahidDjinnOfTheLamp.class));
        cards.add(new SetCardInfo("Zulaport Cutthroat", 36, Rarity.UNCOMMON, mage.cards.z.ZulaportCutthroat.class));
    }
}