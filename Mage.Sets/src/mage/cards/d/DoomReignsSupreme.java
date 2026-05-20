package mage.cards.d;

import java.util.Optional;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class DoomReignsSupreme extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "a Villain you control");

    public DoomReignsSupreme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.PLAN);

        // Whenever a Villain you control enters, each opponent loses 1 life and you gain 1 life. Put a plan counter on this enchantment.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new LoseLifeOpponentsEffect(1), filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()));
        this.addAbility(ability);

        // When the fifth plan counter is put on this enchantment, sacrifice it. When you do, target opponent exiles the top five cards of
        // their library. You may cast up to two spells from among the exiled cards without paying their mana costs.
        this.addAbility(new DoomReignsSupremeCounterTriggeredAbility());
    }

    private DoomReignsSupreme(final DoomReignsSupreme card) {
        super(card);
    }

    @Override
    public DoomReignsSupreme copy() {
        return new DoomReignsSupreme(this);
    }
}

class DoomReignsSupremeCounterTriggeredAbility extends TriggeredAbilityImpl {

    private static DoWhenCostPaid makeEffect() {
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new DoomReignsSupremeExileEffect(), false,
            "target opponent exiles the top five cards of their library. "
            + "You may cast up to two spells from among the exiled cards without paying their mana costs"
        );
        reflexive.addTarget(new TargetOpponent());
        return new DoWhenCostPaid(reflexive, new SacrificeSourceCost(), "Sacrifice {this}?", false);
    }

    DoomReignsSupremeCounterTriggeredAbility() {
        super(Zone.ALL, makeEffect(), false);
        setTriggerPhrase("When the fifth plan counter is put on {this}, ");
    }

    private DoomReignsSupremeCounterTriggeredAbility(final DoomReignsSupremeCounterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DoomReignsSupremeCounterTriggeredAbility copy() {
        return new DoomReignsSupremeCounterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId()) || !event.getData().equals(CounterType.PLAN.getName())) {
            return false;
        }
        int amountAdded = event.getAmount();
        Permanent sourcePermanent = Optional
            .ofNullable(game.getPermanent(getSourceId()))
            .orElse(game.getPermanentEntering(getSourceId()));
        int planCounters;
        if (sourcePermanent != null) {
            planCounters = sourcePermanent.getCounters(game).getCount(CounterType.PLAN);
        } else {
            planCounters = amountAdded;
        }
        return planCounters - amountAdded < 5 && 5 <= planCounters;
    }
}

class DoomReignsSupremeExileEffect extends OneShotEffect {

    DoomReignsSupremeExileEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles the top five cards of their library. "
            + "You may cast up to two spells from among the exiled cards without paying their mana costs";
    }

    private DoomReignsSupremeExileEffect(final DoomReignsSupremeExileEffect effect) {
        super(effect);
    }

    @Override
    public DoomReignsSupremeExileEffect copy() {
        return new DoomReignsSupremeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 5));
        opponent.moveCards(cards, Zone.EXILED, source, game);
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD_NON_LAND, 2);
        return true;
    }
}
