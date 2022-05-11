
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class JaceCunningCastawayIllusionToken extends TokenImpl {

    public JaceCunningCastawayIllusionToken() {
        super("Illusion Token", "2/2 blue Illusion creature token with \"When this creature becomes the target of a spell, sacrifice it.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);

        subtype.add(SubType.ILLUSION);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new IllusionTokenTriggeredAbility());
    }

    public JaceCunningCastawayIllusionToken(final JaceCunningCastawayIllusionToken token) {
        super(token);
    }

    @Override
    public JaceCunningCastawayIllusionToken copy() {
        return new JaceCunningCastawayIllusionToken(this);
    }
}

class IllusionTokenTriggeredAbility extends TriggeredAbilityImpl {

    public IllusionTokenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    public IllusionTokenTriggeredAbility(final IllusionTokenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IllusionTokenTriggeredAbility copy() {
        return new IllusionTokenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (event.getTargetId().equals(this.getSourceId()) && eventSourceObject instanceof Spell) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When this creature becomes the target of a spell, sacrifice it.";
    }

}
