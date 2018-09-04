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

        cards.add(new SetCardInfo("Boros Challenger", 156, Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Conclave Tribunal", 6, Rarity.UNCOMMON, mage.cards.c.ConclaveTribunal.class));
        cards.add(new SetCardInfo("Emmara, Soul of the Accord", 168, Rarity.RARE, mage.cards.e.EmmaraSoulOfTheAccord.class));
        cards.add(new SetCardInfo("Firemind's Research", 171, Rarity.RARE, mage.cards.f.FiremindsResearch.class));
        cards.add(new SetCardInfo("Impervious Greatwurm", 273, Rarity.MYTHIC, mage.cards.i.ImperviousGreatwurm.class));
        cards.add(new SetCardInfo("Legion Warboss", 109, Rarity.RARE, mage.cards.l.LegionWarboss.class));
        cards.add(new SetCardInfo("Narcomoeba", 47, Rarity.RARE, mage.cards.n.Narcomoeba.class));
        cards.add(new SetCardInfo("Necrotic Wound", 79, Rarity.UNCOMMON, mage.cards.n.NecroticWound.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 253, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Quasiduplicate", 51, Rarity.RARE, mage.cards.q.Quasiduplicate.class));
        cards.add(new SetCardInfo("Ral, Izzet Viceroy", 195, Rarity.MYTHIC, mage.cards.r.RalIzzetViceroy.class));
        cards.add(new SetCardInfo("Sacred Foundry", 254, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Sinister Sabotage", 54, Rarity.UNCOMMON, mage.cards.s.SinisterSabotage.class));
        cards.add(new SetCardInfo("Steam Vents", 257, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Temple Garden", 258, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Thought Erasure", 206, Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Underrealm Lich", 211, Rarity.MYTHIC, mage.cards.u.UnderrealmLich.class));
        cards.add(new SetCardInfo("Watery Grave", 259, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
