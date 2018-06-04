
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class CoreSet2019 extends ExpansionSet {

    private static final CoreSet2019 instance = new CoreSet2019();

    public static CoreSet2019 getInstance() {
        return instance;
    }

    private CoreSet2019() {
        super("Core Set 2019", "M19", ExpansionSet.buildDate(2018, 7, 13), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;
        cards.add(new SetCardInfo("Act of Treason", 127, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Anticipate", 44, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Cancel", 48, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Catalyst Elemental", 132, Rarity.COMMON, mage.cards.c.CatalystElemental.class));
        cards.add(new SetCardInfo("Daybreak Chaplain", 10, Rarity.COMMON, mage.cards.d.DaybreakChaplain.class));
        cards.add(new SetCardInfo("Disperse", 50, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Dwarven Priest", 11, Rarity.COMMON, mage.cards.d.DwarvenPriest.class));
        cards.add(new SetCardInfo("Dwindle", 53, Rarity.COMMON, mage.cards.d.Dwindle.class));
        cards.add(new SetCardInfo("Fire Elemental", 141, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 56, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Gearsmith Prodigy", 57, Rarity.COMMON, mage.cards.g.GearsmithProdigy.class));
        cards.add(new SetCardInfo("Goblin Instigator", 142, Rarity.COMMON, mage.cards.g.GoblinInstigator.class));
        cards.add(new SetCardInfo("Goblin Motivator", 143, Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Hostile Minotaur", 147, Rarity.COMMON, mage.cards.h.HostileMinotaur.class));
        cards.add(new SetCardInfo("Inspired Charge", 15, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 18, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Knight's Pledge", 19, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Lava Axe", 150, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Loxodon Line Breaker", 24, Rarity.COMMON, mage.cards.l.LoxodonLineBreaker.class));
        cards.add(new SetCardInfo("Mighty Leap", 28, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Omenspeaker", 64, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Onakke Ogre", 153, Rarity.COMMON, mage.cards.o.OnakkeOgre.class));
        cards.add(new SetCardInfo("Oreskos Swiftclaw", 31, Rarity.COMMON, mage.cards.o.OreskosSwiftclaw.class));
        cards.add(new SetCardInfo("Revitalize", 35, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Rustwing Falcon", 36, Rarity.COMMON, mage.cards.r.RustwingFalcon.class));
        cards.add(new SetCardInfo("Salvager of Secrets", 70, Rarity.COMMON, mage.cards.s.SalvagerOfSecrets.class));
        cards.add(new SetCardInfo("Scholar of Stars", 71, Rarity.COMMON, mage.cards.s.ScholarOfStars.class));
        cards.add(new SetCardInfo("Skyscanner", 245, Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Sovereign's Bite", 120, Rarity.COMMON, mage.cards.s.SovereignsBite.class));
        cards.add(new SetCardInfo("Sure Strike", 161, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Tolarian Scholar", 80, Rarity.COMMON, mage.cards.t.TolarianScholar.class));
        cards.add(new SetCardInfo("Tormenting Voice", 164, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Trumpet Blast", 165, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Trusty Packbeast", 41, Rarity.COMMON, mage.cards.t.TrustyPackbeast.class));
        cards.add(new SetCardInfo("Uncomfortable Chill", 82, Rarity.COMMON, mage.cards.u.UncomfortableChill.class));
        cards.add(new SetCardInfo("Wall of Mist", 83, Rarity.COMMON, mage.cards.w.WallOfMist.class));
    }
}
