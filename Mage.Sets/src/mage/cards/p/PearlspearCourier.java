
package mage.cards.p;

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
import mage.abilities.keyword.VigilanceAbility;
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
public final class PearlspearCourier extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldier creature");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public PearlspearCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may choose not to untap Pearlspear Courier during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}{W}, {tap}: Target Soldier creature gets +2/+2 and has vigilance for as long as Pearlspear Courier remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(2, 2, Duration.Custom), SourceTappedCondition.TAPPED,
            "target Soldier creature gets +2/+2"), new ManaCostsImpl<>("{2}{W}"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(),
            Duration.Custom), SourceTappedCondition.TAPPED,"and has vigilance for as long as {this} remains tapped"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private PearlspearCourier(final PearlspearCourier card) {
        super(card);
    }

    @Override
    public PearlspearCourier copy() {
        return new PearlspearCourier(this);
    }
}
