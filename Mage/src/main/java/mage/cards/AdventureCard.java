package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class AdventureCard extends CardImpl {

    private SpellAbility adventureSpellAbility;

    protected AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] mainTypes, CardType[] adventureTypes, String mainCosts, String adventureName, String adventureCosts) {
        super(ownerId, setInfo, mainTypes, mainCosts);
        adventureSpellAbility = new SpellAbility(new ManaCostsImpl(adventureCosts), adventureName, Zone.HAND, SpellAbilityType.ADVENTURE);
        if (Arrays.stream(adventureTypes).anyMatch(CardType.INSTANT::equals)) {
            adventureSpellAbility.setTiming(TimingRule.INSTANT);
        }
        adventureSpellAbility.setSourceId(this.getId());
    }

    protected AdventureCard(final AdventureCard card) {
        super(card);
        this.adventureSpellAbility = card.getAdventureSpellAbility().copy();
    }

    public SpellAbility getAdventureSpellAbility() {
        return adventureSpellAbility;
    }
}
