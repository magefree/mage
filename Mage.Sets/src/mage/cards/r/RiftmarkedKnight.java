package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.RiftmarkedKnightToken;

/**
 *
 * @author JRHerlehy
 */
public final class RiftmarkedKnight extends CardImpl {

    public RiftmarkedKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black; flanking
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        this.addAbility(new FlankingAbility());

        // Suspend 3-{1}{W}{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{W}{W}"), this));

        // When the last time counter is removed from Riftmarked Knight while it's exiled, put a 2/2 black Knight creature token with flanking, protection from white, and haste onto the battlefield.
        this.addAbility(new RiftmarkedKnightTriggeredAbility());
    }

    private RiftmarkedKnight(final RiftmarkedKnight card) {
        super(card);
    }

    @Override
    public RiftmarkedKnight copy() {
        return new RiftmarkedKnight(this);
    }
}

class RiftmarkedKnightTriggeredAbility extends TriggeredAbilityImpl {

    public RiftmarkedKnightTriggeredAbility() {
        super(Zone.EXILED, new CreateTokenEffect(new RiftmarkedKnightToken()), false);
        setTriggerPhrase("When the last time counter is removed from {this} while it's exiled, ");
    }

    public RiftmarkedKnightTriggeredAbility(final RiftmarkedKnightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (event.getTargetId().equals(this.getSourceId()) && game.getCard(event.getTargetId()).getCounters(game).getCount(CounterType.TIME) == 0);
    }

    @Override
    public RiftmarkedKnightTriggeredAbility copy() {
        return new RiftmarkedKnightTriggeredAbility(this);
    }
}
