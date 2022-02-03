
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class FrightshroudCourier extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creature");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public FrightshroudCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may choose not to untap Frightshroud Courier during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}{B}, {tap}: Target Zombie creature gets +2/+2 and has fear for as long as Frightshroud Courier remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(2, 2, Duration.Custom), SourceTappedCondition.TAPPED,
            "target Zombie creature gets +2/+2"), new ManaCostsImpl("{2}{B}"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityTargetEffect(FearAbility.getInstance(),
            Duration.Custom), SourceTappedCondition.TAPPED,"and has fear for as long as {this} remains tapped"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private FrightshroudCourier(final FrightshroudCourier card) {
        super(card);
    }

    @Override
    public FrightshroudCourier copy() {
        return new FrightshroudCourier(this);
    }
}
