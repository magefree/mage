package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.StaticHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AetherRevolt extends CardImpl {

    public AetherRevolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Revolt -- As long as a permanent you controlled left the battlefield this turn, if a source you control would deal noncombat damage to an opponent or a permanent an opponent controls, it deals that much damage plus 2 instead.
        this.addAbility(new SimpleStaticAbility(new ConditionalReplacementEffect(
                new AetherRevoltEffect(), RevoltCondition.instance
        ).setText("As long as a permanent you controlled left the battlefield this turn, "
                + "if a source you control would deal noncombat damage to an opponent or a permanent an opponent controls, "
                + "it deals that much damage plus 2 instead")
        ).setAbilityWord(AbilityWord.REVOLT).addHint(RevoltCondition.getHint()), new RevoltWatcher());

        // Whenever you get one or more {E}, Aether Revolt deals that much damage to any target.
        this.addAbility(new AetherRevoltTriggeredAbility());
    }

    private AetherRevolt(final AetherRevolt card) {
        super(card);
    }

    @Override
    public AetherRevolt copy() {
        return new AetherRevolt(this);
    }
}

class AetherRevoltEffect extends ReplacementEffectImpl {

    AetherRevoltEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to an opponent or a permanent an opponent controls, " +
                "it deals that much damage plus 2 instead";
    }

    private AetherRevoltEffect(final AetherRevoltEffect effect) {
        super(effect);
    }

    @Override
    public AetherRevoltEffect copy() {
        return new AetherRevoltEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return source.isControlledBy(game.getControllerId(event.getSourceId()))
                && !((DamageEvent) event).isCombatDamage()
                && (player.hasOpponent(event.getTargetId(), game)
                || player.hasOpponent(game.getControllerId(event.getTargetId()), game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }
}

class AetherRevoltTriggeredAbility extends TriggeredAbilityImpl {

    AetherRevoltTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        this.addTarget(new TargetAnyTarget());
    }

    private AetherRevoltTriggeredAbility(final AetherRevoltTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AetherRevoltTriggeredAbility copy() {
        return new AetherRevoltTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getData().equals(CounterType.ENERGY.getName())
                || !getControllerId().equals(event.getTargetId())) {
            return false;
        }
        int amount = event.getAmount();
        if (amount <= 0) {
            return false;
        }
        this.getEffects().clear();
        this.getEffects().add(new DamageTargetEffect(amount));
        this.addHint(new StaticHint("Energy you got: " + amount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you get one or more {E}, {this} deals that much damage to any target.";
    }
}
