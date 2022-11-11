package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThassasIntervention extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public ThassasIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}");

        // Choose one-
        // • Look at the top X cards of your library. Put up to two of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                ManacostVariableValue.REGULAR, 2, PutCards.HAND, PutCards.BOTTOM_RANDOM, true));

        // • Counter target spell unless its controller pays twice {X}.
        Mode mode = new Mode(new CounterUnlessPaysEffect(xValue)
                .setText("Counter target spell unless its controller pays twice {X}."));
        mode.addTarget(new TargetSpell());
        this.getSpellAbility().addMode(mode);
    }

    private ThassasIntervention(final ThassasIntervention card) {
        super(card);
    }

    @Override
    public ThassasIntervention copy() {
        return new ThassasIntervention(this);
    }
}
