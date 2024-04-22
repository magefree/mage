
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author Loki, North
 */
public final class MolderBeast extends CardImpl {

    public MolderBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new MolderBeastTriggeredAbility());
    }

    private MolderBeast(final MolderBeast card) {
        super(card);
    }

    @Override
    public MolderBeast copy() {
        return new MolderBeast(this);
    }
}

class MolderBeastTriggeredAbility extends TriggeredAbilityImpl {

    public MolderBeastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), false);
    }

    private MolderBeastTriggeredAbility(final MolderBeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget().isArtifact(game);
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into a graveyard from the battlefield, {this} gets +2/+0 until end of turn.";
    }

    @Override
    public MolderBeastTriggeredAbility copy() {
        return new MolderBeastTriggeredAbility(this);
    }
}