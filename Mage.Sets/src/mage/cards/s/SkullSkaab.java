package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkullSkaab extends CardImpl {

    public SkullSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exploit
        this.addAbility(new ExploitAbility());

        // Whenever a creature you control exploits a nontoken creature, create a 2/2 black Zombie creature token.
        this.addAbility(new SkullSkaabTriggeredAbility());
    }

    private SkullSkaab(final SkullSkaab card) {
        super(card);
    }

    @Override
    public SkullSkaab copy() {
        return new SkullSkaab(this);
    }
}

class SkullSkaabTriggeredAbility extends TriggeredAbilityImpl {

    SkullSkaabTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()));
    }

    private SkullSkaabTriggeredAbility(final SkullSkaabTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkullSkaabTriggeredAbility copy() {
        return new SkullSkaabTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLOITED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent exploiter = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent exploited = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return exploiter != null && exploited != null
                && exploiter.isCreature(game)
                && exploited.isCreature(game)
                && exploiter.isControlledBy(getControllerId())
                && !(exploited instanceof PermanentToken);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control exploits a nontoken creature, " +
                "create a 2/2 black Zombie creature token.";
    }
}
