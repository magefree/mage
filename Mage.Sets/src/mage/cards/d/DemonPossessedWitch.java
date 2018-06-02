
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class DemonPossessedWitch extends CardImpl {

    private static final String rule = "When this creature transforms into Demon-Possessed Witch, you may destroy target creature";

    public DemonPossessedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // When this creature transforms into Demon-Possessed Witch, you may destroy target creature.
        this.addAbility(new DemonPossessedWitchAbility());
    }

    public DemonPossessedWitch(final DemonPossessedWitch card) {
        super(card);
    }

    @Override
    public DemonPossessedWitch copy() {
        return new DemonPossessedWitch(this);
    }
}

class DemonPossessedWitchAbility extends TriggeredAbilityImpl {

    public DemonPossessedWitchAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
        Target target = new TargetPermanent(new FilterCreaturePermanent());
        this.addTarget(target);
    }

    public DemonPossessedWitchAbility(final DemonPossessedWitchAbility ability) {
        super(ability);
    }

    @Override
    public DemonPossessedWitchAbility copy() {
        return new DemonPossessedWitchAbility(this);
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
        return "When this creature transforms into Demon-Possessed Witch, you may destroy target creature.";
    }
}
