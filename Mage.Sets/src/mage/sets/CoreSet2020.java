package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CoreSet2020 extends ExpansionSet {

    private static final CoreSet2020 instance = new CoreSet2020();

    public static CoreSet2020 getInstance() {
        return instance;
    }

    private CoreSet2020() {
        super("Core Set 2020", "M20", ExpansionSet.buildDate(2019, 7, 12), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterSpecial = 0;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;

        cards.add(new SetCardInfo("Angel of Vitality", 4, Rarity.UNCOMMON, mage.cards.a.AngelOfVitality.class));
        cards.add(new SetCardInfo("Chandra's Spitfire", 132, Rarity.UNCOMMON, mage.cards.c.ChandrasSpitfire.class));
        cards.add(new SetCardInfo("Chandra, Acolyte of Flame", 126, Rarity.RARE, mage.cards.c.ChandraAcolyteOfFlame.class));
        cards.add(new SetCardInfo("Chandra, Awakened Inferno", 127, Rarity.MYTHIC, mage.cards.c.ChandraAwakenedInferno.class));
        cards.add(new SetCardInfo("Chandra, Novice Pyromancer", 128, Rarity.UNCOMMON, mage.cards.c.ChandraNovicePyromancer.class));
        cards.add(new SetCardInfo("Cryptic Caves", 244, Rarity.UNCOMMON, mage.cards.c.CrypticCaves.class));
        cards.add(new SetCardInfo("Devout Decree", 013, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Disenchant", 14, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dragon Mage", 135, Rarity.UNCOMMON, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Dread Presence", 96, Rarity.RARE, mage.cards.d.DreadPresence.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 208, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("Infuriate", 145, Rarity.COMMON, mage.cards.i.Infuriate.class));
        cards.add(new SetCardInfo("Leyline of Sanctity", 26, Rarity.RARE, mage.cards.l.LeylineOfSanctity.class));
        cards.add(new SetCardInfo("Octoprophet", 70, Rarity.COMMON, mage.cards.o.Octoprophet.class));
        cards.add(new SetCardInfo("Unsummon", 78, Rarity.COMMON, mage.cards.u.Unsummon.class));
    }
}
