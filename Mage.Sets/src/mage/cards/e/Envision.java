package mage.cards.e;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryMayPutToBottomEffect;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class Envision extends CardImpl {

    public Envision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");
        

        // Look at the top card of target player's library. You may have that player put that card on the bottom of his or her library.
        this.getSpellAbility().addEffect(new EnvisionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Envision(final Envision card) {
        super(card);
    }

    @Override
    public Envision copy() {
        return new Envision(this);
    }
}

class EnvisionEffect extends OneShotEffect {

    public EnvisionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top card of target player's library. You may have that player put that card on the bottom of his or her library";
    }

    public EnvisionEffect(final EnvisionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null || target == null || controller == null) {
            return false;
        }
        if (!target.getLibrary().isEmptyDraw()) {
            Card card = target.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            target.lookAtCards(sourceObject.getName(), new CardsImpl(card), game);
            boolean toBottom = controller.chooseUse(outcome, "Put card on the bottom of target player's library?", source, game);
            return target.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, !toBottom, false);
        }
        return true;
    }

    @Override
    public EnvisionEffect copy() {
        return new EnvisionEffect(this);
    }

}
