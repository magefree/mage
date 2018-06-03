
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class AwokenHorror extends CardImpl {

    public AwokenHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.KRAKEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);
        this.color.setBlue(true);

        this.nightCard = true;

        // When this creature transforms into Awoken Horrow, return all non-Horror creatures to their owners' hands.
        this.addAbility(new AwokenHorrorAbility());
    }

    public AwokenHorror(final AwokenHorror card) {
        super(card);
    }

    @Override
    public AwokenHorror copy() {
        return new AwokenHorror(this);
    }
}

class AwokenHorrorAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Horror creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.HORROR)));
    }

    public AwokenHorrorAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandFromBattlefieldAllEffect(filter), false);
    }

    public AwokenHorrorAbility(final AwokenHorrorAbility ability) {
        super(ability);
    }

    @Override
    public AwokenHorrorAbility copy() {
        return new AwokenHorrorAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into Awoken Horror, return all non-Horror creatures to their owners' hands.";
    }
}
