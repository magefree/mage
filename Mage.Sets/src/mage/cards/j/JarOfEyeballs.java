package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author North
 */
public final class JarOfEyeballs extends CardImpl {

    public JarOfEyeballs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature you control dies, put two eyeball counters on Jar of Eyeballs.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.EYEBALL.createInstance(2)),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // {3}, {tap}, Remove all eyeball counters from Jar of Eyeballs:
        // Look at the top X cards of your library, where X is the number of eyeball counters removed this way.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new JarOfEyeballsEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.EYEBALL));
        this.addAbility(ability);
    }

    private JarOfEyeballs(final JarOfEyeballs card) {
        super(card);
    }

    @Override
    public JarOfEyeballs copy() {
        return new JarOfEyeballs(this);
    }
}

class JarOfEyeballsEffect extends OneShotEffect {

    public JarOfEyeballsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library, where X is the number of eyeball counters removed this way. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public JarOfEyeballsEffect(final JarOfEyeballsEffect effect) {
        super(effect);
    }

    @Override
    public JarOfEyeballsEffect copy() {
        return new JarOfEyeballsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int countersRemoved = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, countersRemoved));
        controller.lookAtCards(source, null, cards, game);
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
        if (controller.choose(Outcome.DrawCard, cards, target, game)) {
            Cards targetCards = new CardsImpl(target.getTargets());
            controller.moveCards(targetCards, Zone.HAND, source, game);
            cards.removeAll(targetCards);
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
