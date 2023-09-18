package mage.cards.i;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiredIdea extends CardImpl {

    public InspiredIdea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Cleave {3}{U}{U}
        this.addAbility(new CleaveAbility(this, new DrawCardSourceControllerEffect(3), "{3}{U}{U}"));

        // Draw three cards. [Your maximum hand size is reduced by three for the rest of the game.]
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
                3, Duration.EndOfGame, MaximumHandSizeControllerEffect.HandSizeModification.REDUCE
        ).setText("[Your maximum hand size is reduced by three for the rest of the game.]"));
    }

    private InspiredIdea(final InspiredIdea card) {
        super(card);
    }

    @Override
    public InspiredIdea copy() {
        return new InspiredIdea(this);
    }
}
