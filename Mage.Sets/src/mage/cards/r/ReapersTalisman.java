package mage.cards.r;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class ReapersTalisman extends CardImpl {

    public ReapersTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, it gains deathtouch until end of turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.EndOfTurn,
                "it gains deathtouch until end of turn"
        )));

        // Whenever equipped creature attacks alone, defending player loses 2 life and you gain 2 life.
        this.addAbility(new ReapersTalismanAttacksLoneTriggeredAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ReapersTalisman(final ReapersTalisman card) {
        super(card);
    }

    @Override
    public ReapersTalisman copy() {
        return new ReapersTalisman(this);
    }
}

class ReapersTalismanAttacksLoneTriggeredAbility extends TriggeredAbilityImpl {

    public ReapersTalismanAttacksLoneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
        this.addEffect(new GainLifeEffect(2));
    }

    private ReapersTalismanAttacksLoneTriggeredAbility(final ReapersTalismanAttacksLoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReapersTalismanAttacksLoneTriggeredAbility copy() {
        return new ReapersTalismanAttacksLoneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId) && game.getCombat().attacksAlone()) {
            Permanent equipment = game.getPermanent(this.sourceId);
            UUID attackerId = game.getCombat().getAttackers().get(0);
            if (equipment != null && equipment.isAttachedTo(attackerId)) {
                UUID defender = game.getCombat().getDefendingPlayerId(attackerId, game);
                if (defender != null) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(defender));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks alone, defending player loses 2 life and you gain 2 life.";
    }
}
