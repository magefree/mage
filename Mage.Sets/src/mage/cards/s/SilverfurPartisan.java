package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SilverfurPartisan extends CardImpl {

    public SilverfurPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, create a 2/2 green Wolf creature token.
        this.addAbility(new CreaturesYouControlBecomesTargetTriggeredAbility(new CreateTokenEffect(new WolfToken())));
    }

    private SilverfurPartisan(final SilverfurPartisan card) {
        super(card);
    }

    @Override
    public SilverfurPartisan copy() {
        return new SilverfurPartisan(this);
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
        MageObject object = game.getObject(event.getSourceId());
        if (permanent != null
                && object != null
                && permanent.isControlledBy(this.controllerId)
                && (permanent.hasSubtype(SubType.WOLF, game)
                || permanent.hasSubtype(SubType.WEREWOLF, game))) {
            if (object instanceof StackObject) {
                if (object.isInstant(game)
                        || object.isSorcery(game)) {
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
        return "Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, create a 2/2 green Wolf creature token.";
    }
}
