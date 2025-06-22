package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Reconnaissance extends CardImpl {

    private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking creature you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public Reconnaissance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // {0}: Remove target attacking creature you control from combat and untap it.
        Ability ability = new SimpleActivatedAbility(new RemoveFromCombatTargetEffect()
                .setText("remove target attacking creature you control from combat"), new GenericManaCost(0));
        ability.addEffect(new UntapTargetEffect("and untap it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Reconnaissance(final Reconnaissance card) {
        super(card);
    }

    @Override
    public Reconnaissance copy() {
        return new Reconnaissance(this);
    }
}
