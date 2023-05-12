
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class RashkaTheSlayer extends CardImpl {

    public RashkaTheSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Rashka the Slayer blocks one or more black creatures, Rashka gets +1/+2 until end of turn.
        this.addAbility(new RashkaTheSlayerTriggeredAbility(new BoostSourceEffect(1, 2, Duration.EndOfTurn)));
    }

    private RashkaTheSlayer(final RashkaTheSlayer card) {
        super(card);
    }

    @Override
    public RashkaTheSlayer copy() {
        return new RashkaTheSlayer(this);
    }
}

class RashkaTheSlayerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RashkaTheSlayerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever {this} blocks a " + filter.getMessage() + ", " );
    }

    public RashkaTheSlayerTriggeredAbility(final RashkaTheSlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent blocked = game.getPermanent(event.getTargetId());
            if (blocked != null && filter.match(blocked, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RashkaTheSlayerTriggeredAbility copy() {
        return new RashkaTheSlayerTriggeredAbility(this);
    }
}
