package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pj21
 */
public class JudgeGiftCards2021 extends ExpansionSet {

    private static final JudgeGiftCards2021 instance = new JudgeGiftCards2021();

    public static JudgeGiftCards2021 getInstance() {
        return instance;
    }

    private JudgeGiftCards2021() {
        super("Judge Gift Cards 2021", "PJ21", ExpansionSet.buildDate(2021, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Edgar Markov", 3, Rarity.MYTHIC, mage.cards.e.EdgarMarkov.class));
        cards.add(new SetCardInfo("Ezuri, Claw of Progress", 4, Rarity.MYTHIC, mage.cards.e.EzuriClawOfProgress.class));
        cards.add(new SetCardInfo("Grand Arbiter Augustin IV", 6, Rarity.RARE, mage.cards.g.GrandArbiterAugustinIV.class));
        cards.add(new SetCardInfo("Karlov of the Ghost Council", 7, Rarity.MYTHIC, mage.cards.k.KarlovOfTheGhostCouncil.class));
        cards.add(new SetCardInfo("K'rrik, Son of Yawgmoth", 2, Rarity.RARE, mage.cards.k.KrrikSonOfYawgmoth.class));
        cards.add(new SetCardInfo("Mizzix of the Izmagnus", 8, Rarity.MYTHIC, mage.cards.m.MizzixOfTheIzmagnus.class));
        cards.add(new SetCardInfo("Morophon, the Boundless", 1, Rarity.MYTHIC, mage.cards.m.MorophonTheBoundless.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Arisen", 9, Rarity.MYTHIC, mage.cards.n.NicolBolasTheArisen.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Ravager", 9, Rarity.MYTHIC, mage.cards.n.NicolBolasTheRavager.class));
        cards.add(new SetCardInfo("The Gitrog Monster", 5, Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("Zacama, Primal Calamity", 10, Rarity.MYTHIC, mage.cards.z.ZacamaPrimalCalamity.class));
    }
}
