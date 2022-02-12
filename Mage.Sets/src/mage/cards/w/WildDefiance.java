package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public final class WildDefiance extends CardImpl {

    public WildDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        this.addAbility(new CreaturesYouControlBecomesTargetTriggeredAbility(new BoostTargetEffect(3, 3, Duration.EndOfTurn)));
    }

    private WildDefiance(final WildDefiance card) {
        super(card);
    }

    @Override
    public WildDefiance copy() {
        return new WildDefiance(this);
    }
}

class CreaturesYouControlBecomesTargetTriggeredAbility extends TriggeredAbilityImpl {

    public CreaturesYouControlBecomesTargetTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public CreaturesYouControlBecomesTargetTriggeredAbility(final CreaturesYouControlBecomesTargetTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreaturesYouControlBecomesTargetTriggeredAbility copy() {
        return new CreaturesYouControlBecomesTargetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null 
                && permanent.isControlledBy(this.controllerId) 
                && permanent.isCreature(game)) {
            MageObject object = game.getObject(event.getSourceId());
            if (object instanceof Spell) {
                Card c = (Spell) object;
                if (c.isInstantOrSorcery(game)) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.";
    }
}
