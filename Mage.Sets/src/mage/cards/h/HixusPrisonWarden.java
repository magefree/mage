
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class HixusPrisonWarden extends CardImpl {

    public HixusPrisonWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        this.addAbility(new HixusPrisonWardenTriggeredAbility(new HixusPrisonWardenExileEffect()));
    }

    private HixusPrisonWarden(final HixusPrisonWarden card) {
        super(card);
    }

    @Override
    public HixusPrisonWarden copy() {
        return new HixusPrisonWarden(this);
    }
}

class HixusPrisonWardenTriggeredAbility extends TriggeredAbilityImpl {

    public HixusPrisonWardenTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
    }

    public HixusPrisonWardenTriggeredAbility(final HixusPrisonWardenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HixusPrisonWardenTriggeredAbility copy() {
        return new HixusPrisonWardenTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        MageObject mageObject = getSourceObject(game);
        return (mageObject instanceof Permanent) && ((Permanent) mageObject).getTurnsOnBattlefield() == 0;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (damageEvent.getPlayerId().equals(getControllerId())
                && damageEvent.isCombatDamage()
                && sourcePermanent != null
                && sourcePermanent.isCreature(game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, if {this} entered the battlefield this turn, exile that creature until {this} leaves the battlefield.";
    }

}

class HixusPrisonWardenExileEffect extends OneShotEffect {

    public HixusPrisonWardenExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile that creature until {this} leaves the battlefield";
    }

    public HixusPrisonWardenExileEffect(final HixusPrisonWardenExileEffect effect) {
        super(effect);
    }

    @Override
    public HixusPrisonWardenExileEffect copy() {
        return new HixusPrisonWardenExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        // If Prison Warden leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            Effect effect = new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName());
            effect.setTargetPointer(getTargetPointer());
            return effect.apply(game, source);
        }
        return false;
    }
}
