package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Snarespinner extends CardImpl {

    public Snarespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Snarespinner blocks a creature with flying, Snarespinner gets +2/+0 until end of turn.
        this.addAbility(new SnarespinnerTriggeredAbility());
    }

    private Snarespinner(final Snarespinner card) {
        super(card);
    }

    @Override
    public Snarespinner copy() {
        return new Snarespinner(this);
    }
}

class SnarespinnerTriggeredAbility extends TriggeredAbilityImpl {

    SnarespinnerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), false);
    }

    private SnarespinnerTriggeredAbility(final SnarespinnerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId())
                && game.getPermanent(event.getTargetId()).getAbilities().containsKey(FlyingAbility.getInstance().getId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks a creature with flying, {this} gets +2/+0 until end of turn.";
    }

    @Override
    public SnarespinnerTriggeredAbility copy() {
        return new SnarespinnerTriggeredAbility(this);
    }
}