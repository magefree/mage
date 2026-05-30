package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroesCommander extends ExpansionSet {

    private static final MarvelSuperHeroesCommander instance = new MarvelSuperHeroesCommander();

    public static MarvelSuperHeroesCommander getInstance() {
        return instance;
    }

    private MarvelSuperHeroesCommander() {
        super("Marvel Super Heroes Commander", "MSC", ExpansionSet.buildDate(2026, 6, 26), SetType.SUPPLEMENTAL);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Captain America, Team Leader", 5, Rarity.MYTHIC, mage.cards.c.CaptainAmericaTeamLeader.class));
        cards.add(new SetCardInfo("Dark Ritual", 793, Rarity.UNCOMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Hit-Monkey", 723, Rarity.RARE, mage.cards.h.HitMonkey.class));
        cards.add(new SetCardInfo("Human Torch", 3, Rarity.MYTHIC, mage.cards.h.HumanTorch.class));
        cards.add(new SetCardInfo("Invisible Woman", 1, Rarity.MYTHIC, mage.cards.i.InvisibleWoman.class));
        cards.add(new SetCardInfo("Lucky the Pizza Dog", 726, Rarity.RARE, mage.cards.l.LuckyThePizzaDog.class));
        cards.add(new SetCardInfo("Mister Fantastic", 2, Rarity.MYTHIC, mage.cards.m.MisterFantastic.class));
        cards.add(new SetCardInfo("T'Challa, the Black Panther", 7, Rarity.MYTHIC, mage.cards.t.TChallaTheBlackPanther.class));
        cards.add(new SetCardInfo("The Fantastic Four", 741, Rarity.MYTHIC, mage.cards.t.TheFantasticFour.class));
        cards.add(new SetCardInfo("The Thing", 4, Rarity.MYTHIC, mage.cards.t.TheThing.class));
        cards.add(new SetCardInfo("Vision, Synthezoid Avenger", 460, Rarity.RARE, mage.cards.v.VisionSynthezoidAvenger.class));

        //TODO: Correct these collectors numbers and rarities once confirmed
        cards.add(new SetCardInfo("Abomination, Irradiated Brute", "XXX1", Rarity.RARE, mage.cards.a.AbominationIrradiatedBrute.class));
        cards.add(new SetCardInfo("Loki, God of Lies", "XXX2", Rarity.RARE, mage.cards.l.LokiGodOfLies.class));
        cards.add(new SetCardInfo("M.O.D.O.K., Evil Intellect", "XXX3", Rarity.RARE, mage.cards.m.MODOKEvilIntellect.class));
        cards.add(new SetCardInfo("Thanos, Death's Consort", "XXX4", Rarity.RARE, mage.cards.t.ThanosDeathsConsort.class));
        cards.add(new SetCardInfo("Ultron, Machine Overlord", "XXX5", Rarity.RARE, mage.cards.u.UltronMachineOverlord.class));
        cards.add(new SetCardInfo("Absorbing Man and Titania", "XXX6", Rarity.RARE, mage.cards.a.AbsorbingManAndTitania.class));
    }
}
