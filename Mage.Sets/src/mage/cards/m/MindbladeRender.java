package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class MindbladeRender extends CardImpl {

    public MindbladeRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.
        this.addAbility(new MindbladeRenderTriggeredAbility());
    }

    private MindbladeRender(final MindbladeRender card) {
        super(card);
    }

    @Override
    public MindbladeRender copy() {
        return new MindbladeRender(this);
    }
}

class MindbladeRenderTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedForCombatDamageStep;

    public MindbladeRenderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new LoseLifeSourceControllerEffect(1));
        this.usedForCombatDamageStep = false;
    }

    private MindbladeRenderTriggeredAbility(final MindbladeRenderTriggeredAbility effect) {
        super(effect);
        this.usedForCombatDamageStep = effect.usedForCombatDamageStep;
    }

    @Override
    public MindbladeRenderTriggeredAbility copy() {
        return new MindbladeRenderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
            return false;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return false;
        }
        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent attacker = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (attacker == null) {
            return false;
        }
        if (((DamagedPlayerEvent) event).isCombatDamage()
                && controller.hasOpponent(event.getTargetId(), game)
                && attacker.hasSubtype(SubType.WARRIOR, game)
                && !usedForCombatDamageStep) {
            usedForCombatDamageStep = true;
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.";
    }
}
