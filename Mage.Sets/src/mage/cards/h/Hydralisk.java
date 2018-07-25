package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author NinthWorld
 */
public final class Hydralisk extends CardImpl {

    public Hydralisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hydralisk attacks and at least one attacking creature isn't blocked, Hydralisk gets +1/+1 until end of turn.
        this.addAbility(new HydraliskAbility());
    }

    public Hydralisk(final Hydralisk card) {
        super(card);
    }

    @Override
    public Hydralisk copy() {
        return new Hydralisk(this);
    }
}

class HydraliskAbility extends TriggeredAbilityImpl {

    public HydraliskAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
    }

    public HydraliskAbility(final HydraliskAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (sourcePermanent != null && sourcePermanent.isAttacking()) {
            for(CombatGroup combatGroup : game.getCombat().getGroups()) {
                if(combatGroup.getBlockers().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public HydraliskAbility copy() {
        return new HydraliskAbility(this);
    }
}