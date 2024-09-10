package mage.cards.t;

import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TerrorTide extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT, -1);

    public TerrorTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Fathomless descent -- All creatures get -X/-X until end of turn, where X is the number of permanent cards in your graveyard.
        this.getSpellAbility().addEffect(
                new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false,
                        "All creatures get -X/-X until end of turn, where X is the number of permanent cards in your graveyard")
        );
        this.getSpellAbility().setAbilityWord(AbilityWord.FATHOMLESS_DESCENT).addHint(DescendCondition.getHint());
    }

    private TerrorTide(final TerrorTide card) {
        super(card);
    }

    @Override
    public TerrorTide copy() {
        return new TerrorTide(this);
    }
}
