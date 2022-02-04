
package mage.cards.g;

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
import mage.abilities.keyword.ShroudAbility;
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
public final class GhosthelmCourier extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizard creature");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public GhosthelmCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may choose not to untap Ghosthelm Courier during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}{U}, {tap}: Target Wizard creature gets +2/+2 and has shroud for as long as Ghosthelm Courier remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(2, 2, Duration.Custom), SourceTappedCondition.TAPPED,
            "target Wizard creature gets +2/+2"), new ManaCostsImpl("{2}{U}"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityTargetEffect(ShroudAbility.getInstance(),
            Duration.Custom), SourceTappedCondition.TAPPED,"and has shroud for as long as {this} remains tapped"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GhosthelmCourier(final GhosthelmCourier card) {
        super(card);
    }

    @Override
    public GhosthelmCourier copy() {
        return new GhosthelmCourier(this);
    }
}
