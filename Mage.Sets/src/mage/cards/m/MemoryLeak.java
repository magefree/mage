package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
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
public final class MemoryLeak extends CardImpl {

    public MemoryLeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it.
        this.getSpellAbility().addEffect(new MemoryLeakEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private MemoryLeak(final MemoryLeak card) {
        super(card);
    }

    @Override
    public MemoryLeak copy() {
        return new MemoryLeak(this);
    }
}

class MemoryLeakEffect extends OneShotEffect {

    MemoryLeakEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals their hand. "
                + "You choose a nonland card from that player's graveyard "
                + "or hand and exile it.";
    }

    private MemoryLeakEffect(final MemoryLeakEffect effect) {
        super(effect);
    }

    @Override
    public MemoryLeakEffect copy() {
        return new MemoryLeakEffect(this);
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
        if (controller.chooseUse(outcome, "Exile a card from hand or graveyard?", null, "Hand", "Graveyard", source, game)) {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s hand");
            target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            cards = opponent.getHand();
        } else {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s graveyard");
            target = new TargetCard(Zone.GRAVEYARD, filter);
            target.setNotTarget(true);
            cards = opponent.getGraveyard();
        }
        if (controller.choose(outcome, cards, target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
        }
        return true;
    }
}
