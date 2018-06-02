
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class LastRites extends CardImpl {

    public LastRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Discard any number of cards. Target player reveals their hand, then you choose a nonland card from it for each card discarded this way. That player discards those cards.
        this.getSpellAbility().addEffect(new LastRitesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public LastRites(final LastRites card) {
        super(card);
    }

    @Override
    public LastRites copy() {
        return new LastRites(this);
    }
}

class LastRitesEffect extends OneShotEffect {

    LastRitesEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard any number of cards. Target player reveals their hand, then you choose a nonland card from it for each card discarded this way. That player discards those cards";
    }

    LastRitesEffect(final LastRitesEffect effect) {
        super(effect);
    }

    @Override
    public LastRitesEffect copy() {
        return new LastRitesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            Cards cardsInHand = controller.getHand().copy();
            TargetCard target = new TargetCardInHand(0, cardsInHand.size(), new FilterCard("cards to discard"));
            controller.chooseTarget(outcome, cardsInHand, target, source, game);
            int discardCount = target.getTargets().size();
            if (discardCount > 0) {
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.discard(card, source, game);
                    }
                }
                FilterCard filter = new FilterCard((discardCount > 1 ? "" : "a") + " nonland card" + (discardCount > 1 ? "s" : ""));
                filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
                StaticValue discardValue = new StaticValue(discardCount);
                Effect effect = new DiscardCardYouChooseTargetEffect(discardValue, filter, TargetController.ANY);
                effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
