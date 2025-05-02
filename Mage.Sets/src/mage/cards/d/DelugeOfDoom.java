package mage.cards.d;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelugeOfDoom extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(CardTypesInGraveyardCount.YOU);

    public DelugeOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // All creatures get -X/-X until end of turn, where X is the number of card types among cards in your graveyard.
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn)
                .setText("all creatures get -X/-X until end of turn, where X is the number of card types among cards in your graveyard"));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private DelugeOfDoom(final DelugeOfDoom card) {
        super(card);
    }

    @Override
    public DelugeOfDoom copy() {
        return new DelugeOfDoom(this);
    }
}
