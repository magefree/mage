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
        //this.numBoosterLands = 1;
        //this.numBoosterCommon = 10;
        //this.numBoosterUncommon = 3;
        //this.numBoosterRare = 1;
        //this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Impervious Greatwurm", 273, Rarity.MYTHIC, mage.cards.i.ImperviousGreatwurm.class));
    }
}
