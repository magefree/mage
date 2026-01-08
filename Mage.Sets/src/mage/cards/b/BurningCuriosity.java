package mage.cards.b;

import mage.abilities.condition.common.BlightedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.BlightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurningCuriosity extends CardImpl {

    public BurningCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // As an additional cost to cast this spell, you may blight 1.
        this.addAbility(new BlightAbility(1));

        // Exile the top two cards of your library. If this spell's additional cost was paid, exile the top three cards instead. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTopXMayPlayUntilEffect(3, Duration.UntilEndOfYourNextTurn),
                new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn),
                BlightedCondition.instance, "exile the top two cards of your library. " +
                "If this spell's additional cost was paid, exile the top three cards instead. " +
                "Until the end of your next turn, you may play those cards"
        ));
    }

    private BurningCuriosity(final BurningCuriosity card) {
        super(card);
    }

    @Override
    public BurningCuriosity copy() {
        return new BurningCuriosity(this);
    }
}
