package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

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
        this.addAbility(new HixusPrisonWardenTriggeredAbility());
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

    public HixusPrisonWardenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileUntilSourceLeavesEffect());
        addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        setTriggerPhrase("Whenever a creature deals combat damage to you, if {this} entered the battlefield this turn, ");
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
}
