package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/mgb
 */
public class MultiverseGiftBox extends ExpansionSet {

    private static final MultiverseGiftBox instance = new MultiverseGiftBox();

    public static MultiverseGiftBox getInstance() {
        return instance;
    }

    private MultiverseGiftBox() {
        super("Multiverse Gift Box", "MGB", ExpansionSet.buildDate(1996, 11, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bull Elephant", 9, Rarity.RARE, mage.cards.b.BullElephant.class));
        cards.add(new SetCardInfo("Dark Privilege", 3, Rarity.RARE, mage.cards.d.DarkPrivilege.class));
        cards.add(new SetCardInfo("King Cheetah", 10, Rarity.RARE, mage.cards.k.KingCheetah.class));
        cards.add(new SetCardInfo("Necrosavant", 4, Rarity.RARE, mage.cards.n.Necrosavant.class));
        cards.add(new SetCardInfo("Ovinomancer", 2, Rarity.RARE, mage.cards.o.Ovinomancer.class));
        cards.add(new SetCardInfo("Peace Talks", 1, Rarity.RARE, mage.cards.p.PeaceTalks.class));
        cards.add(new SetCardInfo("Urborg Mindsucker", 5, Rarity.RARE, mage.cards.u.UrborgMindsucker.class));
        cards.add(new SetCardInfo("Vampirism", 6, Rarity.RARE, mage.cards.v.Vampirism.class));
        cards.add(new SetCardInfo("Viashino Sandstalker", 8, Rarity.RARE, mage.cards.v.ViashinoSandstalker.class));
        cards.add(new SetCardInfo("Wicked Reward", 7, Rarity.RARE, mage.cards.w.WickedReward.class));
     }
}
