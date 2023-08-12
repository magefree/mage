package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RhythmOfTheWild extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("Creature spells you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RhythmOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                filter, null, Duration.WhileOnBattlefield
        )));

        // Nontoken creatures you control have riot.
        Ability ability = new SimpleStaticAbility(new RhythmOfTheWildEffect());
        ability.addEffect(new GainAbilityControlledEffect(
                new RiotAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ).setText(""));
        this.addAbility(ability);
    }

    private RhythmOfTheWild(final RhythmOfTheWild card) {
        super(card);
    }

    @Override
    public RhythmOfTheWild copy() {
        return new RhythmOfTheWild(this);
    }
}

class RhythmOfTheWildEffect extends ReplacementEffectImpl {

    RhythmOfTheWildEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Nontoken creatures you control have riot. " +
                "<i>(They enter the battlefield with your choice of a +1/+1 counter or haste.)</i>";
    }

    private RhythmOfTheWildEffect(RhythmOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && !(creature instanceof PermanentToken);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player player = game.getPlayer(source.getControllerId());
        if (creature == null || player == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Have " + creature.getLogName() + " enter the battlefield with a +1/+1 counter on it or with haste?",
                null, "+1/+1 counter", "Haste", source, game
        )) {
            game.informPlayers(player.getLogName() + " choose to put a +1/+1 counter on " + creature.getName());
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        } else {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public RhythmOfTheWildEffect copy() {
        return new RhythmOfTheWildEffect(this);
    }
}
