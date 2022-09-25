
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class FlamespeakerAdept extends CardImpl {

    public FlamespeakerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you scry, Flamespeaker Adept gets +2/+0 and gains first strike until end of turn.
        this.addAbility(new ScryTriggeredAbility());
    }

    private FlamespeakerAdept(final FlamespeakerAdept card) {
        super(card);
    }

    @Override
    public FlamespeakerAdept copy() {
        return new FlamespeakerAdept(this);
    }
}
class ScryTriggeredAbility extends TriggeredAbilityImpl {

    public ScryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2,0, Duration.EndOfTurn), false);
        this.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        setTriggerPhrase("Whenever you scry, ");
    }

    public ScryTriggeredAbility(final ScryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScryTriggeredAbility copy() {
        return new ScryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }
}
