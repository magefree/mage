package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgonizingRemorse extends CardImpl {

    public AgonizingRemorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonland card from 
        // it or a card from their graveyard. Exile that card. You lose 1 life.
        this.getSpellAbility().addEffect(new AgonizingRemorseEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private AgonizingRemorse(final AgonizingRemorse card) {
        super(card);
    }

    @Override
    public AgonizingRemorse copy() {
        return new AgonizingRemorse(this);
    }
}

class AgonizingRemorseEffect extends OneShotEffect {

    AgonizingRemorseEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. You choose a nonland card from it "
                + "or a card from their graveyard. Exile that card. You lose 1 life.";
    }

    private AgonizingRemorseEffect(final AgonizingRemorseEffect effect) {
        super(effect);
    }

    @Override
    public AgonizingRemorseEffect copy() {
        return new AgonizingRemorseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target;
        Cards cards;
        if (controller.chooseUse(outcome, "Exile a card from hand or graveyard?", 
                null, "Hand", "Graveyard", source, game)) {
            target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card in " 
                    + opponent.getName() + "'s hand"));
            target.setNotTarget(true);
            cards = opponent.getHand();
        } else {
            target = new TargetCard(Zone.GRAVEYARD, new FilterCard("card in " 
                    + opponent.getName() + "'s graveyard"));
            target.setNotTarget(true);
            cards = opponent.getGraveyard();
        }
        if (controller.choose(outcome, cards, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
        }
        controller.loseLife(1, game, source, false);
        return true;
    }
}
