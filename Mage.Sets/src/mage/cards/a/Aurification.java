package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author andyfries
 */

public final class Aurification extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature with a gold counter on it");

    static {
        filter.add(CounterType.GOLD.getPredicate());
    }

    static final String rule = "Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.";

    public Aurification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Whenever a creature deals damage to you, put a gold counter on it.
        this.addAbility(new AddGoldCountersAbility());

        // Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.
        BecomesSubtypeAllEffect becomesSubtypeAllEffect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.WALL), filter, false);
        becomesSubtypeAllEffect.setText("");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, becomesSubtypeAllEffect));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));

        // When Aurification leaves the battlefield, remove all gold counters from all creatures.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RemoveAllGoldCountersEffect(), false));
    }

    private Aurification(final Aurification card) {
        super(card);
    }

    @Override
    public Aurification copy() {
        return new Aurification(this);
    }

    public static class AddGoldCountersAbility extends TriggeredAbilityImpl {

        public AddGoldCountersAbility() {
            super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.GOLD.createInstance()));
        }

        public AddGoldCountersAbility(final AddGoldCountersAbility ability) {
            super(ability);
        }

        @Override
        public AddGoldCountersAbility copy() {
            return new AddGoldCountersAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.isCreature(game)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a creature deals damage to you, put a gold counter on it.";
        }

    }

    public static class RemoveAllGoldCountersEffect extends OneShotEffect {
        public RemoveAllGoldCountersEffect() {
            super(Outcome.Neutral);
            this.staticText = "remove all gold counters from all creatures";
        }

        public RemoveAllGoldCountersEffect(final RemoveAllGoldCountersEffect effect) {
            super(effect);
        }

        @Override
        public RemoveAllGoldCountersEffect copy() {
            return new RemoveAllGoldCountersEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE, game)) {
                if (permanent != null) {
                    int numToRemove = permanent.getCounters(game).getCount(CounterType.GOLD);
                    if (numToRemove > 0) {
                        permanent.removeCounters(CounterType.GOLD.getName(), numToRemove, source, game);
                    }
                }
            }
            return true;
        }
    }
}