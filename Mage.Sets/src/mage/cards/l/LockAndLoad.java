package mage.cards.l;

import mage.abilities.dynamicvalue.common.InstantAndSorceryCastThisTurn;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LockAndLoad extends CardImpl {

    public LockAndLoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw a card, then draw a card for each other instant and sorcery spell you've cast this turn.
        this.getSpellAbility()
            .addHint(InstantAndSorceryCastThisTurn.YOU.getHint())
            .addEffect(new DrawCardSourceControllerEffect(InstantAndSorceryCastThisTurn.YOU)
                .setText("Draw a card, then draw a card for each other instant and sorcery spell you've cast this turn"));
        // Plot {3}{U}
        this.addAbility(new PlotAbility("{3}{U}"));
    }

    private LockAndLoad(final LockAndLoad card) {
        super(card);
    }

    @Override
    public LockAndLoad copy() {
        return new LockAndLoad(this);
    }
}