package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class GevScaledScorch extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Lizard spell");
    static {
        filter.add(SubType.LIZARD.getPredicate());
    }

    public GevScaledScorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2), false));

        // Other creatures you control enter with an additional +1/+1 counter on them for each opponent who lost life this turn.
        this.addAbility(new SimpleStaticAbility(new GevScaledScorchEntersEffect()));

        // Whenever you cast a Lizard spell, Gev, Scaled Scorch deals 1 damage to target opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(1), filter, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private GevScaledScorch(final GevScaledScorch card) {
        super(card);
    }

    @Override
    public GevScaledScorch copy() {
        return new GevScaledScorch(this);
    }
}


class GevScaledScorchEntersEffect extends ReplacementEffectImpl {

    GevScaledScorchEntersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Other creatures you control enter with an additional +1/+1 counter on them for each opponent who lost life this turn";
    }

    private GevScaledScorchEntersEffect(GevScaledScorchEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (creature == null || controller == null || watcher == null) {
            return false;
        }
        int num = game.getOpponents(controller.getId())
                .stream()
                .mapToInt(watcher::getLifeLost)
                .reduce(0, Integer::sum);
        if (num > 0) {
            creature.addCounters(CounterType.P1P1.createInstance(num), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public GevScaledScorchEntersEffect copy() {
        return new GevScaledScorchEntersEffect(this);
    }
}
