package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcquisitionOctopus extends CardImpl {

    public AcquisitionOctopus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Acquisition Octopus or equipped creature deals combat damage to a player, draw a card.
        this.addAbility(new AcquisitionOctopusTriggeredAbility());

        // Reconfigure {2}
        this.addAbility(new ReconfigureAbility("{2}"));
    }

    private AcquisitionOctopus(final AcquisitionOctopus card) {
        super(card);
    }

    @Override
    public AcquisitionOctopus copy() {
        return new AcquisitionOctopus(this);
    }
}

class AcquisitionOctopusTriggeredAbility extends TriggeredAbilityImpl {

    AcquisitionOctopusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private AcquisitionOctopusTriggeredAbility(final AcquisitionOctopusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AcquisitionOctopusTriggeredAbility copy() {
        return new AcquisitionOctopusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (getSourceId().equals(event.getSourceId())) {
            return true;
        }
        Permanent permanent = getSourcePermanentOrLKI(game);
        return permanent != null && event.getSourceId().equals(permanent.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "Whenever {this} or equipped creature deals combat damage to a player, draw a card.";
    }
}
