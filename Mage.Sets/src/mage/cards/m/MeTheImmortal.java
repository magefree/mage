package mage.cards.m;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCounterChoiceSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Skiwkr
 */
public final class MeTheImmortal extends CardImpl {

    public MeTheImmortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, put your choice of a +1/+1, first strike, vigilance, or menace counter on Me, the Immortal.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCounterChoiceSourceEffect(
                CounterType.P1P1, CounterType.FIRST_STRIKE, CounterType.VIGILANCE, CounterType.MENACE
        ).setText("put your choice of a +1/+1, first strike, vigilance, or menace counter on {this}"),
                TargetController.YOU,
                false));
        // Counters remain on Me as it moves to any zone other than a player's hand or library.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MeTheImmortalEffect()));
        // You may cast Me from your graveyard by discarding two cards in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MeTheImmortalCastEffect())
                .setIdentifier(MageIdentifier.MeTheImmortalAlternateCast));
    }

    private MeTheImmortal(final MeTheImmortal card) {
        super(card);
    }

    @Override
    public MeTheImmortal copy() {
        return new MeTheImmortal(this);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        boolean skullBriarEffectApplied = false;
        if (event.getToZone() != Zone.HAND && event.getToZone() != Zone.LIBRARY) {
            for (StaticAbility ability : getAbilities(game).getStaticAbilities(event.getFromZone())) {
                for (Effect effect : ability.getEffects(game, EffectType.REPLACEMENT)) {
                    if (effect instanceof MeTheImmortalEffect && event.getAppliedEffects().contains(effect.getId())) {
                        skullBriarEffectApplied = true;
                    }
                }
            }
        }
        Counters copyFrom = null;
        if (skullBriarEffectApplied) {
            if (event.getTarget() != null && event.getFromZone() == Zone.BATTLEFIELD) {
                copyFrom = event.getTarget().getCounters(game).copy();
            } else {
                copyFrom = this.getCounters(game).copy();
            }
        }
        super.updateZoneChangeCounter(game, event);
        Counters copyTo = null;
        if (event.getTarget() != null && event.getToZone() == Zone.BATTLEFIELD) {
            if (event.getFromZone() != Zone.BATTLEFIELD) {
                copyTo = event.getTarget().getCounters(game);
            }
        } else {
            copyTo = this.getCounters(game);
        }
        if (copyTo != null && copyFrom != null) {
            for (Counter counter : copyFrom.values()) {
                copyTo.addCounter(counter);
            }
        }
    }
}

class MeTheImmortalEffect extends ReplacementEffectImpl {

    MeTheImmortalEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "Counters remain on {this} as it moves to any zone other than a player's hand or library.";
    }

    private MeTheImmortalEffect(final MeTheImmortalEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }

    @Override
    public MeTheImmortalEffect copy() {
        return new MeTheImmortalEffect(this);
    }
}

class MeTheImmortalCastEffect extends AsThoughEffectImpl {

    MeTheImmortalCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard " +
                "by discarding two cards in addition to paying its other costs";
    }

    private MeTheImmortalCastEffect(final MeTheImmortalCastEffect effect) {
        super(effect);
    }

    @Override
    public MeTheImmortalCastEffect copy() {
        return new MeTheImmortalCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.getSourceId().equals(objectId)
                || !source.isControlledBy(affectedControllerId)
                || game.getState().getZone(objectId) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        controller.setCastSourceIdWithAlternateMana(
                objectId, new ManaCostsImpl<>("{2}{G}{U}{R}"), costs,
                MageIdentifier.MeTheImmortalAlternateCast
        );
        return true;
    }
}