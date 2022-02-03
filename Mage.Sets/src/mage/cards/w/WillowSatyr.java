package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WillowSatyr extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public WillowSatyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Willow Satyr during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target legendary creature for as long as you control Willow Satyr and Willow Satyr remains tapped.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.WhileControlled), SourceTappedCondition.TAPPED,
                "Gain control of target legendary creature for as long as you control {this} and {this} remains tapped"
        ), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private WillowSatyr(final WillowSatyr card) {
        super(card);
    }

    @Override
    public WillowSatyr copy() {
        return new WillowSatyr(this);
    }
}
