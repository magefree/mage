package mage.cards.f;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class FlowOfKnowledge extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ISLAND));
    private static final Hint hint = new ValueHint("Islands you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ISLAND);

    public FlowOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Draw a card for each Island you control, then discard two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(2).concatBy(", then"));
        this.getSpellAbility().addHint(hint);
    }

    private FlowOfKnowledge(final FlowOfKnowledge card) {
        super(card);
    }

    @Override
    public FlowOfKnowledge copy() {
        return new FlowOfKnowledge(this);
    }
}
