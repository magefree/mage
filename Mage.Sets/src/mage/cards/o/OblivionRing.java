

package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class OblivionRing extends CardImpl {

    private static final FilterNonlandPermanent anotherNonlandPermanent = new FilterNonlandPermanent("another target nonland permanent");

    static {
         anotherNonlandPermanent.add(AnotherPredicate.instance);
    }

    public OblivionRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // When Oblivion Ring enters the battlefield, exile another target nonland permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addTarget(new TargetPermanent(anotherNonlandPermanent));
        this.addAbility(ability);

        // When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private OblivionRing(final OblivionRing card) {
        super(card);
    }

    @Override
    public OblivionRing copy() {
        return new OblivionRing(this);
    }

}
