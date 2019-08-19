
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class GrappleWithThePast extends CardImpl {

    public GrappleWithThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Put the top three cards of your library into your graveyard, then you may return a creature or land card from your graveyard to your hand.
        getSpellAbility().addEffect(new PutTopCardOfLibraryIntoGraveControllerEffect(3));
        getSpellAbility().addEffect(new GrappleWithThePastEffect());
    }

    public GrappleWithThePast(final GrappleWithThePast card) {
        super(card);
    }

    @Override
    public GrappleWithThePast copy() {
        return new GrappleWithThePast(this);
    }
}

class GrappleWithThePastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or land card from your graveyard");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public GrappleWithThePastEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then you may return a creature or land card from your graveyard to your hand";
    }

    public GrappleWithThePastEffect(final GrappleWithThePastEffect effect) {
        super(effect);
    }

    @Override
    public GrappleWithThePastEffect copy() {
        return new GrappleWithThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (target.hasPossibleTargets(source.getSourceId(), source.getControllerId(), game)
                && controller.chooseUse(outcome, "Return a creature or land card from your graveyard to hand?", source, game)
                && controller.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
