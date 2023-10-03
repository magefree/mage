
package mage.cards.i;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ImpulsiveWager extends CardImpl {

    public ImpulsiveWager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast Irresponsible Gambling, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));

        // If the discarded card was a nonland card, draw two cards. Otherwise, put a bounty counter on target creature.
        this.getSpellAbility().addEffect(new ImpulsiveWagerEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private ImpulsiveWager(final ImpulsiveWager card) {
        super(card);
    }

    @Override
    public ImpulsiveWager copy() {
        return new ImpulsiveWager(this);
    }
}

class ImpulsiveWagerEffect extends OneShotEffect {

    public ImpulsiveWagerEffect() {
        super(Outcome.Benefit);
        staticText = "If the discarded card was a nonland card, draw two cards. Otherwise, put a bounty counter on target creature";
    }

    private ImpulsiveWagerEffect(final ImpulsiveWagerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DiscardCardCost cost = (DiscardCardCost) source.getCosts().get(0);
            if (cost != null) {
                List<Card> cards = cost.getCards();
                if (cards.size() == 1 && cards.get(0).isLand(game)) {
                    Effect effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
                    effect.setTargetPointer(getTargetPointer());
                    effect.apply(game, source);
                } else {
                    player.drawCards(2, source, game);
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public ImpulsiveWagerEffect copy() {
        return new ImpulsiveWagerEffect(this);
    }
}
