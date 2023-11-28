package mage.cards.t;

import mage.Mana;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheCore extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT);

    public TheCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // (Transforms from Matzalantli.)
        this.nightCard = true;

        // Fathomless descent -- {T}: Add X mana of any one color, where X is the number of permanent cards in your graveyard.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), xValue, new TapSourceCost(),
                "Add X mana of any one color, where X is the number of permanent cards in your graveyard.",
                true
        ).setAbilityWord(AbilityWord.FATHOMLESS_DESCENT).addHint(DescendCondition.getHint()));
    }

    private TheCore(final TheCore card) {
        super(card);
    }

    @Override
    public TheCore copy() {
        return new TheCore(this);
    }
}
