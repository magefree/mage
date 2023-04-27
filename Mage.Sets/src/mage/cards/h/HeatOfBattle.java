package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public final class HeatOfBattle extends CardImpl {

    public HeatOfBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");


        // Whenever a creature blocks, Heat of Battle deals 1 damage to that creature's controller.
        this.addAbility(new HeatOfBattleTriggeredAbility());
    }

    private HeatOfBattle(final HeatOfBattle card) {
        super(card);
    }

    public HeatOfBattle copy() {
        return new HeatOfBattle(this);
    }
}

class HeatOfBattleTriggeredAbility extends TriggeredAbilityImpl {

    public HeatOfBattleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public HeatOfBattleTriggeredAbility(HeatOfBattleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeatOfBattleTriggeredAbility copy() {
        return new HeatOfBattleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getTargetId());
        if (blocker != null) {
            getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks, {this} deals 1 damage to that creature's controller.";
    }
}

