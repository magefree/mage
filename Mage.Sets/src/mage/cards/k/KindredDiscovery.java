
package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
/**
 *
 * @author Saga
 */
public final class KindredDiscovery extends CardImpl {

    public KindredDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // As Kindred Discovery enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.DrawCard)));
        
        // Whenever a creature you control of the chosen type enters the battlefield or attacks, draw a card.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature you control of the chosen type");
        filter.add(ChosenSubtypePredicate.TRUE);
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, false));
    }

    private KindredDiscovery(final KindredDiscovery card) {
        super(card);
    }

    @Override
    public KindredDiscovery copy() {
        return new KindredDiscovery(this);
    }
}