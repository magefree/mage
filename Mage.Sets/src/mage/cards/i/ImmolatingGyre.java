package mage.cards.i;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImmolatingGyre extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public ImmolatingGyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Immolating Gyre deals X damage to each creature and planeswalker you don't control, where X is the number of instant and sorcery cards in your graveyard.
        this.getSpellAbility().addEffect(new DamageAllEffect(xValue, filter).setText(
                "{this} deals X damage to each creature and planeswalker you don't control, " +
                        "where X is the number of instant and sorcery cards in your graveyard"
        ));
    }

    private ImmolatingGyre(final ImmolatingGyre card) {
        super(card);
    }

    @Override
    public ImmolatingGyre copy() {
        return new ImmolatingGyre(this);
    }
}
