package mage.cards.w;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WisdomOfAges extends CardImpl {

    public WisdomOfAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        // Return all instant and sorcery cards from your graveyard to your hand. You have no maximum hand size for the rest of the game.
        this.getSpellAbility().addEffect(new ReturnToHandFromGraveyardAllEffect(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, TargetController.YOU));
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.EndOfGame, MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));

        // Exile Wisdom of Ages.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private WisdomOfAges(final WisdomOfAges card) {
        super(card);
    }

    @Override
    public WisdomOfAges copy() {
        return new WisdomOfAges(this);
    }
}
