
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
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
 * @author jeffwadsworth
 */
public final class PerimeterCaptain extends CardImpl {

    public PerimeterCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever a creature you control with defender blocks, you may gain 2 life.
        this.addAbility(new PerimeterCaptainTriggeredAbility(new GainLifeEffect(2), true));
    }

    private PerimeterCaptain(final PerimeterCaptain card) {
        super(card);
    }

    @Override
    public PerimeterCaptain copy() {
        return new PerimeterCaptain(this);
    }
}

class PerimeterCaptainTriggeredAbility extends TriggeredAbilityImpl {

    public PerimeterCaptainTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a creature you control with defender blocks, ");
    }

    public PerimeterCaptainTriggeredAbility(final PerimeterCaptainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature != null) {
            if (creature.isControlledBy(this.getControllerId()) && creature.getAbilities().contains(DefenderAbility.getInstance())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PerimeterCaptainTriggeredAbility copy() {
        return new PerimeterCaptainTriggeredAbility(this);
    }
}