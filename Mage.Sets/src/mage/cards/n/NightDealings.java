package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class NightDealings extends CardImpl {

    public NightDealings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a source you control deals damage to another player, put that many theft counters on Night Dealings.
        this.addAbility((new NightDealingsTriggeredAbility()));

        // {2}{B}{B}, Remove X theft counters from Night Dealings: Search your library for a nonland card with converted mana cost X, reveal it, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new NightDealingsSearchEffect(), new ManaCostsImpl<>("{2}{B}{B}"));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.THEFT.createInstance(1)));
        this.addAbility(ability);
    }

    private NightDealings(final NightDealings card) {
        super(card);
    }

    @Override
    public NightDealings copy() {
        return new NightDealings(this);
    }

    private static final class NightDealingsTriggeredAbility extends TriggeredAbilityImpl {

        private NightDealingsTriggeredAbility() {
            super(Zone.BATTLEFIELD, new NightDealingsEffect());
            setTriggerPhrase("Whenever a source you control deals damage to another player, ");
        }

        private NightDealingsTriggeredAbility(final NightDealingsTriggeredAbility ability) {
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
            if (this.isControlledBy(event.getTargetId())
                    || !this.isControlledBy(game.getControllerId(event.getSourceId()))) {
                return false;
            }
            this.getEffects().setValue("damageAmount", event.getAmount());
            return true;
        }
    }

    private static final class NightDealingsEffect extends OneShotEffect {

        private NightDealingsEffect() {
            super(Outcome.Damage);
            this.staticText = "put that many theft counters on {this}";
        }

        private NightDealingsEffect(final NightDealingsEffect effect) {
            super(effect);
        }

        @Override
        public NightDealingsEffect copy() {
            return new NightDealingsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            Integer damageAmount = (Integer) this.getValue("damageAmount");
            return permanent != null
                    && damageAmount != null
                    && damageAmount > 0
                    && permanent.addCounters(
                    CounterType.THEFT.createInstance(damageAmount),
                    source.getControllerId(), source, game
            );
        }
    }

    private static final class NightDealingsSearchEffect extends OneShotEffect {

        private NightDealingsSearchEffect() {
            super(Outcome.DrawCard);
            this.staticText = "Search your library for a nonland card with mana value X, " +
                    "reveal it, put it into your hand, then shuffle";
        }

        private NightDealingsSearchEffect(final NightDealingsSearchEffect effect) {
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
                    break;
                }
            }

            FilterCard filter = new FilterNonlandCard("nonland card with mana value " + cmc);
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            player.searchLibrary(target, source, game);
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                player.revealCards(source, new CardsImpl(card), game);
                player.moveCards(card, Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
    }
}
