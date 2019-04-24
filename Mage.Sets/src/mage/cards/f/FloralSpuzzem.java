
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class FloralSpuzzem extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact defending player controls");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new DefendingPlayerControlsPredicate());
    }

    public FloralSpuzzem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Floral Spuzzem attacks and isn't blocked, you may destroy target artifact defending player controls. If you do, Floral Spuzzem assigns no combat damage this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public FloralSpuzzem(final FloralSpuzzem card) {
        super(card);
    }

    @Override
    public FloralSpuzzem copy() {
        return new FloralSpuzzem(this);
    }
}
