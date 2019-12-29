package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SepharaSkysBlade extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("untapped creatures you control with flying");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    public SepharaSkysBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // You may pay {W} and tap four untapped creatures you control with flying rather than pay this spell's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new ManaCostsImpl("{W}"));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(
                4, 4, filter, true
        )));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Other creatures you control with flying have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private SepharaSkysBlade(final SepharaSkysBlade card) {
        super(card);
    }

    @Override
    public SepharaSkysBlade copy() {
        return new SepharaSkysBlade(this);
    }
}
