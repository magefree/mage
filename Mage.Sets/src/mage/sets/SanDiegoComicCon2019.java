package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class SanDiegoComicCon2019 extends ExpansionSet {

    private static final SanDiegoComicCon2019 instance = new SanDiegoComicCon2019();

    public static SanDiegoComicCon2019 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2019() {
        super("San Diego Comic-Con 2019", "PS19", ExpansionSet.buildDate(2019, 7, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("God-Eternal Bontu", 92, Rarity.MYTHIC, mage.cards.g.GodEternalBontu.class));
        cards.add(new SetCardInfo("God-Eternal Kefnet", 53, Rarity.MYTHIC, mage.cards.g.GodEternalKefnet.class));
        cards.add(new SetCardInfo("God-Eternal Oketra", 16, Rarity.MYTHIC, mage.cards.g.GodEternalOketra.class));
        cards.add(new SetCardInfo("God-Eternal Rhonas", 163, Rarity.MYTHIC, mage.cards.g.GodEternalRhonas.class));
        cards.add(new SetCardInfo("Nicol Bolas, Dragon-God", 207, Rarity.MYTHIC, mage.cards.n.NicolBolasDragonGod.class));
    }
}
