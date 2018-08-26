
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
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
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class JarOfEyeballs extends CardImpl {

    public JarOfEyeballs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature you control dies, put two eyeball counters on Jar of Eyeballs.
        this.addAbility(new JarOfEyeballsTriggeredAbility());
        // {3}, {tap}, Remove all eyeball counters from Jar of Eyeballs:
        // Look at the top X cards of your library, where X is the number of eyeball counters removed this way.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JarOfEyeballsEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new JarOfEyeballsCost());
        this.addAbility(ability);
    }

    public JarOfEyeballs(final JarOfEyeballs card) {
        super(card);
    }

    @Override
    public JarOfEyeballs copy() {
        return new JarOfEyeballs(this);
    }
}

class JarOfEyeballsTriggeredAbility extends TriggeredAbilityImpl {

    public JarOfEyeballsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.EYEBALL.createInstance(2)));
    }

    public JarOfEyeballsTriggeredAbility(final JarOfEyeballsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JarOfEyeballsTriggeredAbility copy() {
        return new JarOfEyeballsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.isControlledBy(this.getControllerId()) && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, " + super.getRule();
    }
}

class JarOfEyeballsCost extends CostImpl {

    private int removedCounters;

    public JarOfEyeballsCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all eyeball counters from {this}";
    }

    public JarOfEyeballsCost(JarOfEyeballsCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters(game).getCount(CounterType.EYEBALL);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.EYEBALL.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public JarOfEyeballsCost copy() {
        return new JarOfEyeballsCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
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
            if (cost instanceof JarOfEyeballsCost) {
                countersRemoved = ((JarOfEyeballsCost) cost).getRemovedCounters();
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
