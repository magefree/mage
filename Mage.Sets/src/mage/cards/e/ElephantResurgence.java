package mage.cards.e;

import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.ElephantResurgenceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElephantResurgence extends CardImpl {

    public ElephantResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Each player creates a green Elephant creature token. Those creatures have "This creature's power and toughness are each equal to the number of creature cards in its controller's graveyard."
        this.getSpellAbility().addEffect(new CreateTokenAllEffect(
                new ElephantResurgenceToken(), TargetController.EACH_PLAYER
        ));
    }

    private ElephantResurgence(final ElephantResurgence card) {
        super(card);
    }

    @Override
    public ElephantResurgence copy() {
        return new ElephantResurgence(this);
    }
}
