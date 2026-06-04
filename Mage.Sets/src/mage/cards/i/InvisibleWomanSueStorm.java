package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WallColorlessToken;

/**
 *
 * @author muz
 */
public final class InvisibleWomanSueStorm extends CardImpl {

    public InvisibleWomanSueStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you put one or more +1/+1 counters on one or more other Heroes you control, you may create a 0/4 colorless Wall creature token with defender.
        this.addAbility(new InvisibleWomanSueStormAbility());
    }

    private InvisibleWomanSueStorm(final InvisibleWomanSueStorm card) {
        super(card);
    }

    @Override
    public InvisibleWomanSueStorm copy() {
        return new InvisibleWomanSueStorm(this);
    }
}

class InvisibleWomanSueStormAbility extends TriggeredAbilityImpl {

    InvisibleWomanSueStormAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WallColorlessToken()), true);
        setTriggerPhrase("Whenever you put one or more +1/+1 counters on one or more other Heroes you control, ");
    }

    private InvisibleWomanSueStormAbility(final InvisibleWomanSueStormAbility ability) {
        super(ability);
    }

    @Override
    public InvisibleWomanSueStormAbility copy() {
        return new InvisibleWomanSueStormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || !event.getData().equals(CounterType.P1P1.getName())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
            && !event.getTargetId().equals(getSourceId())
            && permanent.isControlledBy(getControllerId())
            && permanent.hasSubtype(SubType.HERO, game);
    }
}
