
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class JourneyToNowhere extends CardImpl {

    public JourneyToNowhere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When Journey to Nowhere enters the battlefield, exile target creature.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(AnotherPredicate.instance);
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Journey to Nowhere leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    private JourneyToNowhere(final JourneyToNowhere card) {
        super(card);
    }

    @Override
    public JourneyToNowhere copy() {
        return new JourneyToNowhere(this);
    }

}
