package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.PestBlackGreenAttacksToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SendInThePest extends CardImpl {

    public SendInThePest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Each opponent discards a card. You create a 1/1 black and green Pest creature token with "Whenever this token attacks, you gain 1 life."
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PestBlackGreenAttacksToken()));
    }

    private SendInThePest(final SendInThePest card) {
        super(card);
    }

    @Override
    public SendInThePest copy() {
        return new SendInThePest(this);
    }
}
