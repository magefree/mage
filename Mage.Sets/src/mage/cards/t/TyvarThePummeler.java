package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyvarThePummeler extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another untapped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public TyvarThePummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap another untapped creature you control: Tyvar, the Pummeler gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(filter))
        );
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);

        // {3}{G}{G}: Creatures you control get +X/+X until end of turn, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                Duration.EndOfTurn
        ), new ManaCostsImpl<>("{3}{G}{G}")).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));
    }

    private TyvarThePummeler(final TyvarThePummeler card) {
        super(card);
    }

    @Override
    public TyvarThePummeler copy() {
        return new TyvarThePummeler(this);
    }
}
