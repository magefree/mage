package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TeenageMutantNinjaTurtlesEternal extends ExpansionSet {

    private static final TeenageMutantNinjaTurtlesEternal instance = new TeenageMutantNinjaTurtlesEternal();

    public static TeenageMutantNinjaTurtlesEternal getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtlesEternal() {
        super("Teenage Mutant Ninja Turtles Eternal", "TMC", ExpansionSet.buildDate(2026, 3, 6), SetType.EXPANSION);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dark Ritual", 131, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Donatello, Rad Scientist", 109, Rarity.MYTHIC, mage.cards.d.DonatelloRadScientist.class));
        cards.add(new SetCardInfo("Donatello, the Brains", 2, Rarity.MYTHIC, mage.cards.d.DonatelloTheBrains.class));
        cards.add(new SetCardInfo("Donnie & April, Adorkable Duo", 111, Rarity.RARE, mage.cards.d.DonnieAndAprilAdorkableDuo.class));
        cards.add(new SetCardInfo("Heroes in a Half Shell", 6, Rarity.MYTHIC, mage.cards.h.HeroesInAHalfShell.class));
        cards.add(new SetCardInfo("Leonardo, Worldly Warrior", 101, Rarity.MYTHIC, mage.cards.l.LeonardoWorldlyWarrior.class));
        cards.add(new SetCardInfo("Leonardo, the Balance", 1, Rarity.MYTHIC, mage.cards.l.LeonardoTheBalance.class));
        cards.add(new SetCardInfo("Michelangelo, the Heart", 5, Rarity.MYTHIC, mage.cards.m.MichelangeloTheHeart.class));
        cards.add(new SetCardInfo("Raphael, the Muscle", 4, Rarity.MYTHIC, mage.cards.r.RaphaelTheMuscle.class));
        cards.add(new SetCardInfo("Splinter, the Mentor", 3, Rarity.MYTHIC, mage.cards.s.SplinterTheMentor.class));
    }
}
