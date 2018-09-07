package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class GuildsOfRavnica extends ExpansionSet {

    private static final GuildsOfRavnica instance = new GuildsOfRavnica();

    public static GuildsOfRavnica getInstance() {
        return instance;
    }

    private GuildsOfRavnica() {
        super("Guilds of Ravnica", "GRN", ExpansionSet.buildDate(2018, 10, 5), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Arboretum Elemental", 122, Rarity.UNCOMMON, mage.cards.a.ArboretumElemental.class));
        cards.add(new SetCardInfo("Blade Instructor", 1, Rarity.COMMON, mage.cards.b.BladeInstructor.class));
        cards.add(new SetCardInfo("Barging Sergeant", 92, Rarity.COMMON, mage.cards.b.BargingSergeant.class));
        cards.add(new SetCardInfo("Boros Challenger", 156, Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Conclave Tribunal", 6, Rarity.UNCOMMON, mage.cards.c.ConclaveTribunal.class));
        cards.add(new SetCardInfo("Deadly Visit", 68, Rarity.COMMON, mage.cards.d.DeadlyVisit.class));
        cards.add(new SetCardInfo("Direct Current", 96, Rarity.COMMON, mage.cards.d.DirectCurrent.class));
        cards.add(new SetCardInfo("Emmara, Soul of the Accord", 168, Rarity.RARE, mage.cards.e.EmmaraSoulOfTheAccord.class));
        cards.add(new SetCardInfo("Firemind's Research", 171, Rarity.RARE, mage.cards.f.FiremindsResearch.class));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Golgari Guildgate", 248, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Hammer Dropper", 176, Rarity.COMMON, mage.cards.h.HammerDropper.class));
        cards.add(new SetCardInfo("Healer's Hawk", 14, Rarity.COMMON, mage.cards.h.HealersHawk.class));
        cards.add(new SetCardInfo("Impervious Greatwurm", 273, Rarity.MYTHIC, mage.cards.i.ImperviousGreatwurm.class));
        cards.add(new SetCardInfo("Island", 261, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legion Warboss", 109, Rarity.RARE, mage.cards.l.LegionWarboss.class));
        cards.add(new SetCardInfo("Moodmark Painter", 78, Rarity.COMMON, mage.cards.m.MoodmarkPainter.class));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murmuring Mystic", 45, Rarity.UNCOMMON, mage.cards.m.MurmuringMystic.class));
        cards.add(new SetCardInfo("Narcomoeba", 47, Rarity.RARE, mage.cards.n.Narcomoeba.class));
        cards.add(new SetCardInfo("Necrotic Wound", 79, Rarity.UNCOMMON, mage.cards.n.NecroticWound.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 253, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quasiduplicate", 51, Rarity.RARE, mage.cards.q.Quasiduplicate.class));
        cards.add(new SetCardInfo("Radical Idea", 52, Rarity.COMMON, mage.cards.r.RadicalIdea.class));
        cards.add(new SetCardInfo("Ral, Izzet Viceroy", 195, Rarity.MYTHIC, mage.cards.r.RalIzzetViceroy.class));
        cards.add(new SetCardInfo("Rosemane Centaur", 197, Rarity.COMMON, mage.cards.r.RosemaneCentaur.class));
        cards.add(new SetCardInfo("Sacred Foundry", 254, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Sinister Sabotage", 54, Rarity.UNCOMMON, mage.cards.s.SinisterSabotage.class));
        cards.add(new SetCardInfo("Sonic Assault", 199, Rarity.COMMON, mage.cards.s.SonicAssault.class));
        cards.add(new SetCardInfo("Status // Statue", 230, Rarity.UNCOMMON, mage.cards.s.StatusStatue.class));
        cards.add(new SetCardInfo("Steam Vents", 257, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 258, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Thought Erasure", 206, Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Underrealm Lich", 211, Rarity.MYTHIC, mage.cards.u.UnderrealmLich.class));
        cards.add(new SetCardInfo("Unexplained Disappearance", 56, Rarity.COMMON, mage.cards.u.UnexplainedDisappearance.class));
        cards.add(new SetCardInfo("Wary Okapi", 149, Rarity.COMMON, mage.cards.w.WaryOkapi.class));
        cards.add(new SetCardInfo("Watery Grave", 259, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
