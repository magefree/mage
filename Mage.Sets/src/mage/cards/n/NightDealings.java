
package mage.cards.n;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Loki
 */
public final class NightDealings extends CardImpl {

    public NightDealings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a source you control deals damage to another player, put that many theft counters on Night Dealings.
        this.addAbility((new NightDealingsTriggeredAbility()));

        // {2}{B}{B}, Remove X theft counters from Night Dealings: Search your library for a nonland card with converted mana cost X, reveal it, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NightDealingsSearchEffect(), new ManaCostsImpl("{2}{B}{B}"));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.THEFT.createInstance(1)));
        this.addAbility(ability);
    }

    public NightDealings(final NightDealings card) {
        super(card);
    }

    @Override
    public NightDealings copy() {
        return new NightDealings(this);
    }

    private class NightDealingsTriggeredAbility extends TriggeredAbilityImpl {

        public NightDealingsTriggeredAbility() {
            super(Zone.BATTLEFIELD, new NightDealingsEffect());
        }

        public NightDealingsTriggeredAbility(final NightDealingsTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public NightDealingsTriggeredAbility copy() {
            return new NightDealingsTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            // to another player
            if (!Objects.equals(this.getControllerId(), event.getTargetId())) {
                // a source you control
                UUID sourceControllerId = game.getControllerId(event.getSourceId());
                if (sourceControllerId != null && sourceControllerId.equals(this.getControllerId())) {
                    // save amount of damage to effect
                    this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a source you control deals damage to another player, " + super.getRule();
        }
    }

    private static class NightDealingsEffect extends OneShotEffect {

        public NightDealingsEffect() {
            super(Outcome.Damage);
            this.staticText = "put that many theft counters on {this}";
        }

        public NightDealingsEffect(final NightDealingsEffect effect) {
            super(effect);
        }

        @Override
        public NightDealingsEffect copy() {
            return new NightDealingsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Integer damageAmount = (Integer) this.getValue("damageAmount");
            if (damageAmount != null) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.addCounters(CounterType.THEFT.createInstance(damageAmount), source, game);
                    return true;
                }
            }
            return false;
        }
    }

    private static class NightDealingsSearchEffect extends OneShotEffect {

        public NightDealingsSearchEffect() {
            super(Outcome.DrawCard);
            this.staticText = "Search your library for a nonland card with converted mana cost X, reveal it, and put it into your hand. Then shuffle your library";
        }

        public NightDealingsSearchEffect(final NightDealingsSearchEffect effect) {
            super(effect);
        }

        @Override
        public NightDealingsSearchEffect copy() {
            return new NightDealingsSearchEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }

            int cmc = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    cmc = ((RemoveVariableCountersSourceCost) cost).getAmount();
                }
            }

            FilterNonlandCard filter = new FilterNonlandCard("nonland card with converted mana cost X = " + cmc);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);

            if (player.searchLibrary(target, game)) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);

                    String name = "Reveal";
                    Cards cards = new CardsImpl();
                    cards.add(card);
                    Card sourceCard = game.getCard(source.getSourceId());
                    if (sourceCard != null) {
                        name = sourceCard.getName();
                    }
                    player.revealCards(name, cards, game);
                    game.informPlayers(player.getLogName() + " reveals " + card.getName());
                }
                player.shuffleLibrary(source, game);
                return true;
            }
            player.shuffleLibrary(source, game);
            return false;
        }
    }

}
