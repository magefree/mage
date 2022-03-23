package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WashOut extends CardImpl {

    public WashOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Return all permanents of the color of your choice to their owners' hands.
        this.getSpellAbility().addEffect(new WashOutEffect());
    }

    private WashOut(final WashOut card) {
        super(card);
    }

    @Override
    public WashOut copy() {
        return new WashOut(this);
    }
}

class WashOutEffect extends OneShotEffect {

    public WashOutEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all permanents of the color of your choice to their owners' hands";
    }

    public WashOutEffect(final WashOutEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cardsToReturn = new LinkedHashSet<>();
        ChoiceColor choice = new ChoiceColor();
        if (controller != null 
                && controller.choose(Outcome.ReturnToHand, choice, game)) {
            ObjectColor color = choice.getColor();
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate(color));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                cardsToReturn.add((Card) permanent);
            }
            return controller.moveCards(cardsToReturn, Zone.HAND, source, game);
        }
        return false;
    }

    @Override
    public WashOutEffect copy() {
        return new WashOutEffect(this);
    }
}
