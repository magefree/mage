package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MobilizerMech extends CardImpl {

    public MobilizerMech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Mobilizer Mech becomes crewed, up to one other target Vehicle you control becomes an artifact creature until end of turn.
        this.addAbility(new MobilizerMechTriggeredAbility());

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private MobilizerMech(final MobilizerMech card) {
        super(card);
    }

    @Override
    public MobilizerMech copy() {
        return new MobilizerMech(this);
    }
}

class MobilizerMechTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.VEHICLE, "up to one other Vehicle you control"
    );

    static {
        filter.add(AnotherPredicate.instance);
    }

    MobilizerMechTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE));
        this.addTarget(new TargetPermanent(0, 1, filter));
    }

    private MobilizerMechTriggeredAbility(final MobilizerMechTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MobilizerMechTriggeredAbility copy() {
        return new MobilizerMechTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VEHICLE_CREWED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes crewed, up to one other target Vehicle " +
                "you control becomes an artifact creature until end of turn.";
    }
}
