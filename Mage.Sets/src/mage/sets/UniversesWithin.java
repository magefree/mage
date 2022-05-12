package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class UniversesWithin extends ExpansionSet {

    private static final UniversesWithin instance = new UniversesWithin();

    public static UniversesWithin getInstance() {
        return instance;
    }

    private UniversesWithin() {
        // The set name is a placeholder and will likely change
        super("Universes Within", "SLX", ExpansionSet.buildDate(2022, 3, 3), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Arvinox, the Mind Flail", 1, Rarity.MYTHIC, mage.cards.a.ArvinoxTheMindFlail.class));
        cards.add(new SetCardInfo("Bjorna, Nightfall Alchemist", 2, Rarity.RARE, mage.cards.b.BjornaNightfallAlchemist.class));
        cards.add(new SetCardInfo("Cecily, Haunted Mage", 3, Rarity.RARE, mage.cards.c.CecilyHauntedMage.class));
        cards.add(new SetCardInfo("Elmar, Ulvenwald Informant", 4, Rarity.RARE, mage.cards.e.ElmarUlvenwaldInformant.class));
        cards.add(new SetCardInfo("Hargilde, Kindly Runechanter", 5, Rarity.RARE, mage.cards.h.HargildeKindlyRunechanter.class));
        cards.add(new SetCardInfo("Havengul Laboratory", 9, Rarity.RARE, mage.cards.h.HavengulLaboratory.class));
        cards.add(new SetCardInfo("Havengul Mystery", 9, Rarity.RARE, mage.cards.h.HavengulMystery.class));
        cards.add(new SetCardInfo("Othelm, Sigardian Outcast", 6, Rarity.RARE, mage.cards.o.OthelmSigardianOutcast.class));
        cards.add(new SetCardInfo("Sophina, Spearsage Deserter", 7, Rarity.RARE, mage.cards.s.SophinaSpearsageDeserter.class));
        cards.add(new SetCardInfo("Wernog, Rider's Chaplain", 8, Rarity.RARE, mage.cards.w.WernogRidersChaplain.class));
    }
}
