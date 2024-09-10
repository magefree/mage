
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ValeronWardens extends CardImpl {

    public ValeronWardens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Renown 2
        this.addAbility(new RenownAbility(2));

        // Whenever a creature you control becomes renowned, draw a card.
        this.addAbility(new ValeronWardensTriggeredAbility());
    }

    private ValeronWardens(final ValeronWardens card) {
        super(card);
    }

    @Override
    public ValeronWardens copy() {
        return new ValeronWardens(this);
    }
}

class ValeronWardensTriggeredAbility extends TriggeredAbilityImpl {

    public ValeronWardensTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private ValeronWardensTriggeredAbility(final ValeronWardensTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValeronWardensTriggeredAbility copy() {
        return new ValeronWardensTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_RENOWNED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && permanent.isControlledBy(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes renowned, draw a card.";
    }
}
