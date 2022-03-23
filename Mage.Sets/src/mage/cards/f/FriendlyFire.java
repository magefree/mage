package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FriendlyFire extends CardImpl {

    public FriendlyFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Target creature's controller reveals a card at random from their hand. Friendly Fire deals damage to that creature and that player equal to the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new FriendlyFireEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private FriendlyFire(final FriendlyFire card) {
        super(card);
    }

    @Override
    public FriendlyFire copy() {
        return new FriendlyFire(this);
    }
}

class FriendlyFireEffect extends OneShotEffect {

    public FriendlyFireEffect() {
        super(Outcome.Discard);
        this.staticText = "Target creature's controller reveals a card at random from their hand. {this} deals damage to that creature and that player equal to the revealed card's mana value";
    }

    public FriendlyFireEffect(final FriendlyFireEffect effect) {
        super(effect);
    }

    @Override
    public FriendlyFireEffect copy() {
        return new FriendlyFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                Player controllerOfTargetCreature = game.getPlayer(targetCreature.getControllerId());
                if (controllerOfTargetCreature != null) {
                    if (!controllerOfTargetCreature.getHand().isEmpty()) {
                        Card card = controllerOfTargetCreature.getHand().getRandom(game);
                        if (card != null) {
                            Cards cards = new CardsImpl(card);
                            controllerOfTargetCreature.revealCards(sourceObject.getName(), cards, game);
                            int damage = card.getManaValue();
                            targetCreature.damage(damage, source.getSourceId(), source, game, false, true);
                            controllerOfTargetCreature.damage(damage, source.getSourceId(), source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
