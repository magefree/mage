package mage.cards.r;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RiseFromTheTides extends CardImpl {

    private static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);

    public RiseFromTheTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Create a tapped 2/2 black Zombie creature token for each instant and sorcery card in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), cardsCount, true, false));
        this.getSpellAbility().addHint(new ValueHint("Instant and sorcery card in your graveyard", cardsCount));
    }

    private RiseFromTheTides(final RiseFromTheTides card) {
        super(card);
    }

    @Override
    public RiseFromTheTides copy() {
        return new RiseFromTheTides(this);
    }
}
