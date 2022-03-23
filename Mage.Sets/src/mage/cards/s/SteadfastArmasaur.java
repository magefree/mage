package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
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
 * @author TheElk801
 */
public final class SteadfastArmasaur extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature blocking or blocked by it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public SteadfastArmasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}{W}, {T}: Steadfast Armasaur deals damage equal to its toughness to target creature blocking or blocked by it.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(SourcePermanentToughnessValue.getInstance()), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SteadfastArmasaur(final SteadfastArmasaur card) {
        super(card);
    }

    @Override
    public SteadfastArmasaur copy() {
        return new SteadfastArmasaur(this);
    }
}
