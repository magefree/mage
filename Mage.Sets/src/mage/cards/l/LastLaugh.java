
package mage.cards.l;

import java.util.UUID;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author L_J
 */
public final class LastLaugh extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a permanent other than Last Laugh");
    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public LastLaugh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // Whenever a permanent other than Last Laugh is put into a graveyard from the battlefield, Last Laugh deals 1 damage to each creature and each player.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DamageEverythingEffect(1), false, filter, false));

        // When no creatures are on the battlefield, sacrifice Last Laugh.
        this.addAbility(new LastLaughStateTriggeredAbility());
        
    }

    private LastLaugh(final LastLaugh card) {
        super(card);
    }

    @Override
    public LastLaugh copy() {
        return new LastLaugh(this);
    }
}

class LastLaughStateTriggeredAbility extends StateTriggeredAbility {

    public LastLaughStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public LastLaughStateTriggeredAbility(final LastLaughStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LastLaughStateTriggeredAbility copy() {
        return new LastLaughStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(new FilterCreaturePermanent(), this.getControllerId(), this, game) == 0;
    }

    @Override
    public String getTriggerPhrase() {
        return "When no creatures are on the battlefield, " ;
    }

}
