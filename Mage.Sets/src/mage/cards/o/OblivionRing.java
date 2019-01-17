

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
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.Target;
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
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        Target target = new TargetPermanent(anotherNonlandPermanent);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    public OblivionRing(final OblivionRing card) {
        super(card);
    }

    @Override
    public OblivionRing copy() {
        return new OblivionRing(this);
    }

}
