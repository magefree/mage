package mage.cards.l;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasTriumph extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(SubType.LILIANA);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public LilianasTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Each opponent sacrifices a creature. If you control a Liliana planeswalker, each opponent also discards a card.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardEachPlayerEffect(
                        StaticValue.get(1), false, TargetController.OPPONENT
                ), condition, "If you control a Liliana planeswalker, each opponent also discards a card."
        ));
    }

    private LilianasTriumph(final LilianasTriumph card) {
        super(card);
    }

    @Override
    public LilianasTriumph copy() {
        return new LilianasTriumph(this);
    }
}
