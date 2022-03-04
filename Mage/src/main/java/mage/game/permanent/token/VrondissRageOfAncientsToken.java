package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Arrays;

public final class VrondissRageOfAncientsToken extends TokenImpl {

    public VrondissRageOfAncientsToken() {
        super("Dragon Spirit", "5/4 red and green Dragon Spirit creature token with \"When this creature deals damage, sacrifice it.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(5);
        toughness = new MageInt(4);

        this.addAbility(new VrondissRageOfAncientsTokenTriggeredAbility());

        availableImageSetCodes = Arrays.asList("AFC");
    }

    public VrondissRageOfAncientsToken(final VrondissRageOfAncientsToken token) {
        super(token);
    }

    public VrondissRageOfAncientsToken copy() {
        return new VrondissRageOfAncientsToken(this);
    }
}

class VrondissRageOfAncientsTokenTriggeredAbility extends TriggeredAbilityImpl {

    public VrondissRageOfAncientsTokenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    public VrondissRageOfAncientsTokenTriggeredAbility(final VrondissRageOfAncientsTokenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VrondissRageOfAncientsTokenTriggeredAbility copy() {
        return new VrondissRageOfAncientsTokenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When this creature deals damage, sacrifice it.";
    }
}
