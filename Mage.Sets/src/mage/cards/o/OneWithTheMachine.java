package mage.cards.o;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestConvertedManaCostValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class OneWithTheMachine extends CardImpl {

    public OneWithTheMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw cards equal to the highest converted mana cost among artifacts you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(
                new HighestConvertedManaCostValue()
        ).setText("Draw cards equal to the highest converted mana cost among artifacts you control"));
    }

    public OneWithTheMachine(final OneWithTheMachine card) {
        super(card);
    }

    @Override
    public OneWithTheMachine copy() {
        return new OneWithTheMachine(this);
    }
}
