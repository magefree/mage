
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ForgottenCreation extends CardImpl {

    public ForgottenCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Skulk
        this.addAbility(new SkulkAbility());
        // At the beginning of your upkeep, you may discard all the cards in your hand. If you do, draw that many cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForgottenCreationEffect(), TargetController.YOU, true));
    }

    private ForgottenCreation(final ForgottenCreation card) {
        super(card);
    }

    @Override
    public ForgottenCreation copy() {
        return new ForgottenCreation(this);
    }
}

class ForgottenCreationEffect extends OneShotEffect {

    public ForgottenCreationEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may discard all the cards in your hand. If you do, draw that many cards";
    }

    public ForgottenCreationEffect(final ForgottenCreationEffect effect) {
        super(effect);
    }

    @Override
    public ForgottenCreationEffect copy() {
        return new ForgottenCreationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int cardsInHand = controller.getHand().size();
            if (cardsInHand > 0) {
                controller.discard(cardsInHand, false, false, source, game);
                controller.drawCards(cardsInHand, source, game);
            }
            return true;
        }
        return false;
    }
}
