package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EchoingTruth extends CardImpl {

    public EchoingTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands.
        Target target = new TargetNonlandPermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ReturnToHandAllNamedPermanentsEffect());
    }

    private EchoingTruth(final EchoingTruth card) {
        super(card);
    }

    @Override
    public EchoingTruth copy() {
        return new EchoingTruth(this);
    }
}

class ReturnToHandAllNamedPermanentsEffect extends OneShotEffect {

    public ReturnToHandAllNamedPermanentsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands";
    }

    public ReturnToHandAllNamedPermanentsEffect(final ReturnToHandAllNamedPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandAllNamedPermanentsEffect copy() {
        return new ReturnToHandAllNamedPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && permanent != null) {
            FilterPermanent filter = new FilterPermanent();
            if (CardUtil.haveEmptyName(permanent)) {
                filter.add(new PermanentIdPredicate(permanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(permanent.getName()));
            }
            Cards cardsToHand = new CardsImpl();
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                cardsToHand.add(perm);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return true;
    }
}
