
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class DiregrafCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public DiregrafCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(DeathtouchAbility.getInstance());
        // Other Zombie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        // Whenever another Zombie you control dies, target opponent loses 1 life.
        this.addAbility(new DiregrafCaptainTriggeredAbility());
    }

    public DiregrafCaptain(final DiregrafCaptain card) {
        super(card);
    }

    @Override
    public DiregrafCaptain copy() {
        return new DiregrafCaptain(this);
    }
}

class DiregrafCaptainTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public DiregrafCaptainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
        this.addTarget(new TargetOpponent());
    }

    public DiregrafCaptainTriggeredAbility(final DiregrafCaptainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
                if (p != null && p.isControlledBy(this.controllerId) && filter.match(p, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DiregrafCaptainTriggeredAbility copy() {
        return new DiregrafCaptainTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another Zombie you control dies, target opponent loses 1 life.";
    }
}