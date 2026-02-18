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

        cards.add(new SetCardInfo("April O'Neil, Human Element", 107, Rarity.RARE, mage.cards.a.AprilONeilHumanElement.class));
        cards.add(new SetCardInfo("Commander's Plate", 135, Rarity.MYTHIC, mage.cards.c.CommandersPlate.class));
        cards.add(new SetCardInfo("Dark Leo & Shredder", 99999, Rarity.MYTHIC, mage.cards.d.DarkLeoAndShredder.class));
        cards.add(new SetCardInfo("Dark Ritual", 131, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Donatello, Rad Scientist", 109, Rarity.MYTHIC, mage.cards.d.DonatelloRadScientist.class));
        cards.add(new SetCardInfo("Donatello, the Brains", 2, Rarity.MYTHIC, mage.cards.d.DonatelloTheBrains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Donatello, the Brains", 85, Rarity.MYTHIC, mage.cards.d.DonatelloTheBrains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Donnie & April, Adorkable Duo", 111, Rarity.RARE, mage.cards.d.DonnieAndAprilAdorkableDuo.class));
        cards.add(new SetCardInfo("Food Chain", 133, Rarity.MYTHIC, mage.cards.f.FoodChain.class));
        cards.add(new SetCardInfo("Heroes in a Half Shell", 6, Rarity.MYTHIC, mage.cards.h.HeroesInAHalfShell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroes in a Half Shell", 96, Rarity.MYTHIC, mage.cards.h.HeroesInAHalfShell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leonardo, Cutting Edge", 9999, Rarity.MYTHIC, mage.cards.l.LeonardoCuttingEdge.class));
        cards.add(new SetCardInfo("Leonardo, Worldly Warrior", 101, Rarity.MYTHIC, mage.cards.l.LeonardoWorldlyWarrior.class));
        cards.add(new SetCardInfo("Leonardo, the Balance", 1, Rarity.MYTHIC, mage.cards.l.LeonardoTheBalance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leonardo, the Balance", 83, Rarity.MYTHIC, mage.cards.l.LeonardoTheBalance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Michelangelo, On the Scene", 124, Rarity.MYTHIC, mage.cards.m.MichelangeloOnTheScene.class));
        cards.add(new SetCardInfo("Michelangelo, the Heart", 5, Rarity.MYTHIC, mage.cards.m.MichelangeloTheHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Michelangelo, the Heart", 92, Rarity.MYTHIC, mage.cards.m.MichelangeloTheHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raphael, Tag Team Tough", 118, Rarity.MYTHIC, mage.cards.r.RaphaelTagTeamTough.class));
        cards.add(new SetCardInfo("Raphael, the Muscle", 4, Rarity.MYTHIC, mage.cards.r.RaphaelTheMuscle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raphael, the Muscle", 91, Rarity.MYTHIC, mage.cards.r.RaphaelTheMuscle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Splinter, the Mentor", 3, Rarity.MYTHIC, mage.cards.s.SplinterTheMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Splinter, the Mentor", 89, Rarity.MYTHIC, mage.cards.s.SplinterTheMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Supreme Verdict", 134, Rarity.MYTHIC, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 136, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class));
        cards.add(new SetCardInfo("Waste Not", 132, Rarity.MYTHIC, mage.cards.w.WasteNot.class));
    }
}
