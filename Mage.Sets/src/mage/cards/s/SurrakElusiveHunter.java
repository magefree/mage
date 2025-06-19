package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurrakElusiveHunter extends CardImpl {

    public SurrakElusiveHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control or a creature spell you control becomes the target of a spell or ability an opponent controls, draw a card.
        this.addAbility(new SurrakElusiveHunterTriggeredAbility());
    }

    private SurrakElusiveHunter(final SurrakElusiveHunter card) {
        super(card);
    }

    @Override
    public SurrakElusiveHunter copy() {
        return new SurrakElusiveHunter(this);
    }
}

class SurrakElusiveHunterTriggeredAbility extends TriggeredAbilityImpl {

    SurrakElusiveHunterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever a creature you control or a creature spell you control " +
                "becomes the target of a spell or ability an opponent controls, ");
    }

    private SurrakElusiveHunterTriggeredAbility(final SurrakElusiveHunterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SurrakElusiveHunterTriggeredAbility copy() {
        return new SurrakElusiveHunterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    private boolean checkTargeted(UUID targetId, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
        if (permanent != null) {
            return permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
        }
        Spell spell = game.getSpellOrLKIStack(targetId);
        return spell != null && spell.isCreature(game) && spell.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!checkTargeted(event.getTargetId(), game)) {
            return false;
        }
        StackObject targetingObject = game.findTargetingStackObject(this.getId().toString(), event);
        return targetingObject != null && game.getOpponents(getControllerId()).contains(targetingObject.getControllerId());
    }
}
