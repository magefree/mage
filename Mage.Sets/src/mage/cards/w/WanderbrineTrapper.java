package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WanderbrineTrapper extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("another untapped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public WanderbrineTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}, {T}, Tap another untapped creature you control: Tap target creature an opponent controls.
        Ability ability = new SimpleActivatedAbility(
            new TapTargetEffect(), new ManaCostsImpl<>("{1}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private WanderbrineTrapper(final WanderbrineTrapper card) {
        super(card);
    }

    @Override
    public WanderbrineTrapper copy() {
        return new WanderbrineTrapper(this);
    }
}
