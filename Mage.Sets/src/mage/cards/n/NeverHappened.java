package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class NeverHappened extends CardImpl {

    public NeverHappened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it.
        this.getSpellAbility().addEffect(new NeverHappenedEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public NeverHappened(final NeverHappened card) {
        super(card);
    }

    @Override
    public NeverHappened copy() {
        return new NeverHappened(this);
    }
}

class NeverHappenedEffect extends OneShotEffect {

    public NeverHappenedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals their hand. "
                + "You choose a nonland card from that player's graveyard "
                + "or hand and exile it.";
    }

    public NeverHappenedEffect(final NeverHappenedEffect effect) {
        super(effect);
    }

    @Override
    public NeverHappenedEffect copy() {
        return new NeverHappenedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        Target target;
        if (controller.chooseUse(outcome, "Exile a card from hand or graveyard?", null, "Hand", "Graveyard", source, game)) {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s hand");
            filter.add(new OwnerIdPredicate(opponent.getId()));
            target = new TargetCardInHand(filter);
            target.setNotTarget(true);
        } else {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s graveyard");
            filter.add(new OwnerIdPredicate(opponent.getId()));
            target = new TargetCardInGraveyard(filter);
            target.setNotTarget(true);
        }
        if (controller.choose(outcome, target, source.getSourceId(), game)) {
            controller.moveCardsToExile(game.getCard(target.getFirstTarget()), source, game, false, null, null);
        }
        return true;
    }
}
