
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author North
 */
public final class ExuberantFirestoker extends CardImpl {

    public ExuberantFirestoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if you control a creature with power 5 or greater, you may have Exuberant Firestoker deal 2 damage to target player.
        this.addAbility(new ExuberantFirestokerTriggeredAbility());
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private ExuberantFirestoker(final ExuberantFirestoker card) {
        super(card);
    }

    @Override
    public ExuberantFirestoker copy() {
        return new ExuberantFirestoker(this);
    }
}

class ExuberantFirestokerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public ExuberantFirestokerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), true);
        this.addTarget(new TargetPlayerOrPlaneswalker());
    }

    private ExuberantFirestokerTriggeredAbility(final ExuberantFirestokerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExuberantFirestokerTriggeredAbility copy() {
        return new ExuberantFirestokerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) > 0;
    }

    @Override
    public String getRule() {
        return "At the beginning of your end step, if you control a creature with power 5 or greater, you may have {this} deal 2 damage to target player or planeswalker.";
    }
}
