package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FurnaceCelebration extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FurnaceCelebration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");

        // Whenever you sacrifice another permanent, you may pay {2}. If you do, Furnace Celebration deals 2 damage to any target.
        Ability ability = new SacrificePermanentTriggeredAbility(new DoIfCostPaid(
                new DamageTargetEffect(2), new GenericManaCost(2)), filter);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FurnaceCelebration(final FurnaceCelebration card) {
        super(card);
    }

    @Override
    public FurnaceCelebration copy() {
        return new FurnaceCelebration(this);
    }

}
