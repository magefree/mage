package mage.cards.s;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.InstantSorceryExileGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentineCurve extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            InstantSorceryExileGraveyardCount.instance, StaticValue.get(1)
    );
    private static final Hint hint = new ValueHint(
            "Instant and sorcery cards in your exile and graveyard", InstantSorceryExileGraveyardCount.instance
    );

    public SerpentineCurve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is one plus the total number of instant and sorcery cards you own in exile and in your graveyard.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                xValue, "Put X +1/+1 counters on it, where X is one plus the total number " +
                        "of instant and sorcery cards you own in exile and in your graveyard"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private SerpentineCurve(final SerpentineCurve card) {
        super(card);
    }

    @Override
    public SerpentineCurve copy() {
        return new SerpentineCurve(this);
    }
}
