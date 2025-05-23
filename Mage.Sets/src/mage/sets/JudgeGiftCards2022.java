package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p22
 */
public class JudgeGiftCards2022 extends ExpansionSet {

    private static final JudgeGiftCards2022 instance = new JudgeGiftCards2022();

    public static JudgeGiftCards2022 getInstance() {
        return instance;
    }

    private JudgeGiftCards2022() {
        super("Judge Gift Cards 2022", "P22", ExpansionSet.buildDate(2022, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Animate Dead", 7, Rarity.RARE, mage.cards.a.AnimateDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Greater Auramancy", 1, Rarity.RARE, mage.cards.g.GreaterAuramancy.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 10, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Itlimoc, Cradle of the Sun", 10, Rarity.RARE, mage.cards.i.ItlimocCradleOfTheSun.class));
        cards.add(new SetCardInfo("No Mercy", 9, Rarity.RARE, mage.cards.n.NoMercy.class));
        cards.add(new SetCardInfo("Omniscience", 2, Rarity.RARE, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Parallel Lives", 3, Rarity.RARE, mage.cards.p.ParallelLives.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 8, Rarity.RARE, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Smothering Tithe", 5, Rarity.RARE, mage.cards.s.SmotheringTithe.class));
        cards.add(new SetCardInfo("Stranglehold", 4, Rarity.RARE, mage.cards.s.Stranglehold.class));
        cards.add(new SetCardInfo("Training Grounds", 6, Rarity.RARE, mage.cards.t.TrainingGrounds.class));
    }
}
