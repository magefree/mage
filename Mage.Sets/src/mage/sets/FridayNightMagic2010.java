package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f10
 */
public class FridayNightMagic2010 extends ExpansionSet {

    private static final FridayNightMagic2010 instance = new FridayNightMagic2010();

    public static FridayNightMagic2010 getInstance() {
        return instance;
    }

    private FridayNightMagic2010() {
        super("Friday Night Magic 2010", "F10", ExpansionSet.buildDate(2010, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Anathemancer", 7, Rarity.RARE, mage.cards.a.Anathemancer.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", 3, Rarity.RARE, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 4, Rarity.RARE, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Cloudpost", 5, Rarity.RARE, mage.cards.c.Cloudpost.class));
        cards.add(new SetCardInfo("Elvish Visionary", 6, Rarity.RARE, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Gatekeeper of Malakir", 11, Rarity.RARE, mage.cards.g.GatekeeperOfMalakir.class));
        cards.add(new SetCardInfo("Ghostly Prison", 2, Rarity.RARE, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Krosan Grip", 8, Rarity.RARE, mage.cards.k.KrosanGrip.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 9, Rarity.RARE, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Rift Bolt", 10, Rarity.RARE, mage.cards.r.RiftBolt.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 1, Rarity.RARE, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Wild Nacatl", 12, Rarity.RARE, mage.cards.w.WildNacatl.class));
     }
}
