package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pcmd
 */
public class Commander2011LaunchParty extends ExpansionSet {

    private static final Commander2011LaunchParty instance = new Commander2011LaunchParty();

    public static Commander2011LaunchParty getInstance() {
        return instance;
    }

    private Commander2011LaunchParty() {
        super("Commander 2011 Launch Party", "PCMD", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Basandra, Battle Seraph", 184, Rarity.RARE, mage.cards.b.BasandraBattleSeraph.class));
        cards.add(new SetCardInfo("Edric, Spymaster of Trest", 196, Rarity.RARE, mage.cards.e.EdricSpymasterOfTrest.class));
        cards.add(new SetCardInfo("Nin, the Pain Artist", 213, Rarity.RARE, mage.cards.n.NinThePainArtist.class));
        cards.add(new SetCardInfo("Skullbriar, the Walking Grave", 227, Rarity.RARE, mage.cards.s.SkullbriarTheWalkingGrave.class));
        cards.add(new SetCardInfo("Vish Kal, Blood Arbiter", 234, Rarity.RARE, mage.cards.v.VishKalBloodArbiter.class));
     }
}
