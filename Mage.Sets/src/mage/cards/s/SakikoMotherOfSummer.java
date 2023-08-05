
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SakikoMotherOfSummer extends CardImpl {

    public SakikoMotherOfSummer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control deals combat damage to a player, add that much {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new SakikoMotherOfSummerTriggeredAbility());

    }

    private SakikoMotherOfSummer(final SakikoMotherOfSummer card) {
        super(card);
    }

    @Override
    public SakikoMotherOfSummer copy() {
        return new SakikoMotherOfSummer(this);
    }
}

class SakikoMotherOfSummerTriggeredAbility extends TriggeredAbilityImpl {

    public SakikoMotherOfSummerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public SakikoMotherOfSummerTriggeredAbility(final SakikoMotherOfSummerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SakikoMotherOfSummerTriggeredAbility copy() {
        return new SakikoMotherOfSummerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)) {
                this.getEffects().clear();
                Effect effect = new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(event.getAmount()), "that player", true);
                effect.setTargetPointer(new FixedTarget(creature.getControllerId()));
                effect.setText("add that much {G}. Until end of turn, you don't lose this mana as steps and phases end");
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player, add that much {G}. Until end of turn, you don't lose this mana as steps and phases end.";
    }
}
