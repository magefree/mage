package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class AssassinToken2 extends TokenImpl {

    public AssassinToken2() {
        super("Assassin", "1/1 black Assassin creature token with deathtouch and \"Whenever this creature deals damage to a planeswalker, destroy that planeswalker.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());
        addAbility(new AssassinToken2TriggeredAbility());

        setOriginalExpansionSetCode("WAR");
    }

    private AssassinToken2(final AssassinToken2 token) {
        super(token);
    }

    public AssassinToken2 copy() {
        return new AssassinToken2(this);
    }
}

class AssassinToken2TriggeredAbility extends TriggeredAbilityImpl {

    AssassinToken2TriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private AssassinToken2TriggeredAbility(final AssassinToken2TriggeredAbility effect) {
        super(effect);
    }

    @Override
    public AssassinToken2TriggeredAbility copy() {
        return new AssassinToken2TriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Effect effect = new DestroyTargetEffect();
            effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
            this.getEffects().clear();
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals damage to a planeswalker, destroy that planeswalker.";
    }
}