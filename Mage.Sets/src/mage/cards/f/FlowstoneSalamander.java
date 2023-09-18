package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FlowstoneSalamander extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature blocking it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public FlowstoneSalamander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {R}: Flowstone Salamander deals 1 damage to target creature blocking it.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FlowstoneSalamander(final FlowstoneSalamander card) {
        super(card);
    }

    @Override
    public FlowstoneSalamander copy() {
        return new FlowstoneSalamander(this);
    }
}
