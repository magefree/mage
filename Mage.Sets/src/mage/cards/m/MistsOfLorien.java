package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class MistsOfLorien extends CardImpl {

    public MistsOfLorien(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Replicate {U}
        this.addAbility(new ReplicateAbility("{U}"));

        // Return target nonland permanent and each other nonland permanent with the same mana value as that permanent to their owners' hands.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new MistsOfLorienEffect());

    }

    private MistsOfLorien(final MistsOfLorien card) {
        super(card);
    }

    @Override
    public MistsOfLorien copy() {
        return new MistsOfLorien(this);
    }
}

class MistsOfLorienEffect extends OneShotEffect {

    MistsOfLorienEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent and each other nonland permanent with the same mana value as that permanent to their owners' hands.";
    }

    private MistsOfLorienEffect(final MistsOfLorienEffect effect) {
        super(effect);
    }

    @Override
    public MistsOfLorienEffect copy() {
        return new MistsOfLorienEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && permanent != null) {
            FilterPermanent filter = new FilterNonlandPermanent();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, permanent.getManaValue()));
            Cards cardsToHand = new CardsImpl();
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                cardsToHand.add(perm);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
