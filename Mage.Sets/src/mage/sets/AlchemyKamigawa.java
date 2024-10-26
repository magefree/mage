package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author karapuzz14
 */
public final class AlchemyKamigawa extends ExpansionSet {

    private static final AlchemyKamigawa instance = new AlchemyKamigawa();

    public static AlchemyKamigawa getInstance() {
        return instance;
    }

    private AlchemyKamigawa() {
        super("Alchemy: Kamigawa", "YNEO", ExpansionSet.buildDate(2022, 3, 17), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Boseiju Pathlighter", 26, Rarity.RARE, mage.cards.b.BoseijuPathlighter.class));
        cards.add(new SetCardInfo("Dragonfly Pilot", 1, Rarity.UNCOMMON, mage.cards.d.DragonflyPilot.class));
        cards.add(new SetCardInfo("Experimental Pilot", 6, Rarity.UNCOMMON, mage.cards.e.ExperimentalPilot.class));
        cards.add(new SetCardInfo("Jukai Liberator", 27, Rarity.RARE, mage.cards.j.JukaiLiberator.class));
        cards.add(new SetCardInfo("Kami of Bamboo Groves", 24, Rarity.UNCOMMON, mage.cards.k.KamiOfBambooGroves.class));
        cards.add(new SetCardInfo("Painful Bond", 12, Rarity.UNCOMMON, mage.cards.p.PainfulBond.class));
        cards.add(new SetCardInfo("Swarm Saboteur", 13, Rarity.RARE, mage.cards.s.SwarmSaboteur.class));

    }
}
