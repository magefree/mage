
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author TheElk801
 */
public final class MavrenFeinDuskApostle extends CardImpl {

    public MavrenFeinDuskApostle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more nontoken Vampires you control attack, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new MavrenFeinDuskApostleTriggeredAbility());
    }

    private MavrenFeinDuskApostle(final MavrenFeinDuskApostle card) {
        super(card);
    }

    @Override
    public MavrenFeinDuskApostle copy() {
        return new MavrenFeinDuskApostle(this);
    }
}

class MavrenFeinDuskApostleTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Vampires you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MavrenFeinDuskApostleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new IxalanVampireToken()), false);
        setTriggerPhrase("Whenever one or more nontoken Vampires you control attack, ");
    }

    public MavrenFeinDuskApostleTriggeredAbility(final MavrenFeinDuskApostleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MavrenFeinDuskApostleTriggeredAbility copy() {
        return new MavrenFeinDuskApostleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID creatureId : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null && filter.match(creature, game) && creature.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }
}
