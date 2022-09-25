package mage.cards.d;

import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BatToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesecratedTomb extends CardImpl {

    public DesecratedTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever one or more creature cards leave your graveyard, create a 1/1 black Bat creature token with flying.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new BatToken()), StaticFilters.FILTER_CARD_CREATURES
        ));
    }

    private DesecratedTomb(final DesecratedTomb card) {
        super(card);
    }

    @Override
    public DesecratedTomb copy() {
        return new DesecratedTomb(this);
    }
}
