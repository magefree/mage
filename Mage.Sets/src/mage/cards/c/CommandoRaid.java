package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class CommandoRaid extends CardImpl {

    public CommandoRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Until end of turn, target creature you control gains "When this creature deals combat damage to a player, you may have it deal damage equal to its power to target creature that player controls."
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new CommandoRaidTriggeredAbility(), Duration.EndOfTurn,
                "Until end of turn, target creature you control gains "
                + "\"When this creature deals combat damage to a player, "
                + "you may have it deal damage equal to its power "
                + "to target creature that player controls.\""
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private CommandoRaid(final CommandoRaid card) {
        super(card);
    }

    @Override
    public CommandoRaid copy() {
        return new CommandoRaid(this);
    }
}

class CommandoRaidTriggeredAbility extends TriggeredAbilityImpl {

    public CommandoRaidTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CommandoRaidEffect(), true);
    }

    private CommandoRaidTriggeredAbility(final CommandoRaidTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CommandoRaidTriggeredAbility copy() {
        return new CommandoRaidTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Player opponent = game.getPlayer(event.getPlayerId());
        if (!damageEvent.isCombatDamage()
                || !event.getSourceId().equals(this.getSourceId())
                || opponent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetCreaturePermanent(filter));
        for (Effect effect : this.getAllEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            effect.setValue("damage", event.getAmount());
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When this creature deals combat damage to a player, "
                + "you may have it deal that much damage to target creature that player controls";
    }
}

class CommandoRaidEffect extends OneShotEffect {

    public CommandoRaidEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature that player controls";
    }

    private CommandoRaidEffect(final CommandoRaidEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new DamageTargetEffect((Integer) getValue("damage")).apply(game, source);
    }

    @Override
    public CommandoRaidEffect copy() {
        return new CommandoRaidEffect(this);
    }
}
