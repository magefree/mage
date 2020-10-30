package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorcerersBroom extends CardImpl {

    public SorcerersBroom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice another permanent, you may pay {3}. If you do, create a token that's a copy of Sorcerer's Broom.
        this.addAbility(new SorcerersBroomTriggeredAbility());
    }

    private SorcerersBroom(final SorcerersBroom card) {
        super(card);
    }

    @Override
    public SorcerersBroom copy() {
        return new SorcerersBroom(this);
    }
}

class SorcerersBroomTriggeredAbility extends TriggeredAbilityImpl {

    SorcerersBroomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenCopySourceEffect(), new GenericManaCost(3)));
    }

    private SorcerersBroomTriggeredAbility(final SorcerersBroomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SorcerersBroomTriggeredAbility copy() {
        return new SorcerersBroomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && !event.getTargetId().equals(sourceId);
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice another permanent, you may pay {3}. " +
                "If you do, create a token that's a copy of {this}.";
    }
}