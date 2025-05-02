
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public final class EmperorCrocodile extends CardImpl {

    public EmperorCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When you control no other creatures, sacrifice Emperor Crocodile.
        // 704.1
        this.addAbility(new EmperorCrocodileStateTriggeredAbility());
    }

    private EmperorCrocodile(final EmperorCrocodile card) {
        super(card);
    }

    @Override
    public EmperorCrocodile copy() {
        return new EmperorCrocodile(this);
    }
}

class EmperorCrocodileStateTriggeredAbility extends StateTriggeredAbility {

    public EmperorCrocodileStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private EmperorCrocodileStateTriggeredAbility(final EmperorCrocodileStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmperorCrocodileStateTriggeredAbility copy() {
        return new EmperorCrocodileStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (!perm.getId().equals(this.getSourceId()) && perm.isCreature(game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When you control no other creatures, sacrifice {this}.";
    }
}
