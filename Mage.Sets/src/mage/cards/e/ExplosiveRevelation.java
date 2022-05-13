package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ExplosiveRevelation extends CardImpl {

    public ExplosiveRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Choose any target. Reveal cards from the top of your library until you reveal a nonland card. Explosive Revelation deals damage equal to that card's converted mana cost to that creature or player. Put the nonland card into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new ExplosiveRevelationEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ExplosiveRevelation(final ExplosiveRevelation card) {
        super(card);
    }

    @Override
    public ExplosiveRevelation copy() {
        return new ExplosiveRevelation(this);
    }
}

class ExplosiveRevelationEffect extends OneShotEffect {

    public ExplosiveRevelationEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose any target. Reveal cards from the top of your library until you reveal a nonland card. {this} deals damage equal to that card's mana value to that permanent or player. Put the nonland card into your hand and the rest on the bottom of your library in any order";
    }

    public ExplosiveRevelationEffect(final ExplosiveRevelationEffect effect) {
        super(effect);
    }

    @Override
    public ExplosiveRevelationEffect copy() {
        return new ExplosiveRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                CardsImpl toReveal = new CardsImpl();
                Library library = controller.getLibrary();
                Card nonLandCard = null;
                for (Card card : library.getCards(game)) {
                    toReveal.add(card);
                    if (!card.isLand(game)) {
                        nonLandCard = card;
                        break;
                    }
                }
                // reveal cards
                controller.revealCards(sourceObject.getIdName(), toReveal, game);
                // the nonland card
                int damage = nonLandCard == null ? 0 : nonLandCard.getManaValue();
                // assign damage to target
                for (UUID targetId : targetPointer.getTargets(game, source)) {
                    Permanent targetedCreature = game.getPermanent(targetId);
                    if (targetedCreature != null) {
                        targetedCreature.damage(damage, source.getSourceId(), source, game, false, true);
                    } else {
                        Player targetedPlayer = game.getPlayer(targetId);
                        if (targetedPlayer != null) {
                            targetedPlayer.damage(damage, source.getSourceId(), source, game);
                        }
                    }
                }
                if (nonLandCard != null) {
                    // move nonland card to hand
                    controller.moveCards(nonLandCard, Zone.HAND, source, game);
                    toReveal.remove(nonLandCard);
                }
                // put the rest of the cards on the bottom of the library in any order
                return controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);
            }
            return true;
        }
        return false;
    }
}
