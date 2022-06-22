package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MordantDragon extends CardImpl {

    public MordantDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}: Mordant Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));

        // Whenever Mordant Dragon deals combat damage to a player, you may have it deal that much damage to target creature that player controls.
        this.addAbility(new MordantDragonTriggeredAbility());
    }

    private MordantDragon(final MordantDragon card) {
        super(card);
    }

    @Override
    public MordantDragon copy() {
        return new MordantDragon(this);
    }
}

class MordantDragonTriggeredAbility extends TriggeredAbilityImpl {

    public MordantDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MordantDragonEffect(), true);
    }

    public MordantDragonTriggeredAbility(final MordantDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MordantDragonTriggeredAbility copy() {
        return new MordantDragonTriggeredAbility(this);
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
        return "Whenever {this} deals combat damage to a player, "
                + "you may have it deal that much damage to target creature that player controls.";
    }
}

class MordantDragonEffect extends OneShotEffect {

    public MordantDragonEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature that player controls";
    }

    public MordantDragonEffect(final MordantDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new DamageTargetEffect((Integer) getValue("damage")).apply(game, source);
    }

    @Override
    public MordantDragonEffect copy() {
        return new MordantDragonEffect(this);
    }
}
