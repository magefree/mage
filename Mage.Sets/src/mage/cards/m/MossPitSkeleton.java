package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MossPitSkeleton extends CardImpl {

    public MossPitSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // If Moss-Pit Skeleton was kicked, it enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with three +1/+1 counters on it.", ""
        ));

        // Whenever one or more +1/+1 counters are put on a creature you control, if Moss-Pit Skeleton is in your graveyard, you may put Moss-Pit Skeleton on top of your library.
        this.addAbility(new MossPitSkeletonTriggeredAbility());
    }

    private MossPitSkeleton(final MossPitSkeleton card) {
        super(card);
    }

    @Override
    public MossPitSkeleton copy() {
        return new MossPitSkeleton(this);
    }
}

class MossPitSkeletonTriggeredAbility extends TriggeredAbilityImpl {

    MossPitSkeletonTriggeredAbility() {
        super(Zone.GRAVEYARD, new PutOnLibrarySourceEffect(true), true);
    }

    MossPitSkeletonTriggeredAbility(final MossPitSkeletonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MossPitSkeletonTriggeredAbility copy() {
        return new MossPitSkeletonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return (permanent != null
                    && permanent.isCreature(game)
                    && permanent.isControlledBy(this.getControllerId()));
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getState().getZone(getSourceId()) == Zone.GRAVEYARD;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on a creature you control, " +
                "if {this} is in your graveyard, you may put {this} on top of your library.";
    }
}
