package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class DVanaTendiOptimisticEnsign extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DVanaTendiOptimisticEnsign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORION);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Untap another target permanent.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {3}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new GenericManaCost(3)));
    }

    private DVanaTendiOptimisticEnsign(final DVanaTendiOptimisticEnsign card) {
        super(card);
    }

    @Override
    public DVanaTendiOptimisticEnsign copy() {
        return new DVanaTendiOptimisticEnsign(this);
    }
}
