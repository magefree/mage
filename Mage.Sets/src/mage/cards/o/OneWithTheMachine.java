package mage.cards.o;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneWithTheMachine extends CardImpl {

    public OneWithTheMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw cards equal to the greatest mana value among artifacts you control.
        this.getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS)
                        .setText("Draw cards equal to the greatest mana value among artifacts you control")
        );
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS.getHint());
    }

    private OneWithTheMachine(final OneWithTheMachine card) {
        super(card);
    }

    @Override
    public OneWithTheMachine copy() {
        return new OneWithTheMachine(this);
    }
}
