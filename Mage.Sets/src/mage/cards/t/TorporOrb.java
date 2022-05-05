
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class TorporOrb extends CardImpl {

    public TorporOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TorporOrbEffect()));
    }

    private TorporOrb(final TorporOrb card) {
        super(card);
    }

    @Override
    public TorporOrb copy() {
        return new TorporOrb(this);
    }
}

class TorporOrbEffect extends ContinuousRuleModifyingEffectImpl {

    TorporOrbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Creatures entering the battlefield don't cause abilities to trigger";
    }

    TorporOrbEffect(final TorporOrbEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if (ability != null && ability.getAbilityType() == AbilityType.TRIGGERED) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature(game)) {
                return (((TriggeredAbility) ability).checkTrigger(event, game));
            }
        }
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject enteringObject = game.getObject(event.getSourceId());
        MageObject sourceObject = game.getObject(source);
        Ability ability = (Ability) getValue("targetAbility");
        if (enteringObject != null && sourceObject != null && ability != null) {
            MageObject abilitObject = game.getObject(ability.getSourceId());
            if (abilitObject != null) {
                return sourceObject.getLogName() + " prevented ability of " + abilitObject.getLogName()
                        + " to trigger for " + enteringObject.getLogName() + " entering the battlefield.";
            }
        }
        return null;
    }

    @Override
    public TorporOrbEffect copy() {
        return new TorporOrbEffect(this);
    }

}
