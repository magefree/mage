
package mage.cards.h;

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
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HivisOfTheScale extends CardImpl {

    private static final FilterPermanent filterDragon = new FilterPermanent();

    static {
        filterDragon.add(SubType.DRAGON.getPredicate());
    }

    public HivisOfTheScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You may choose not to untap Hivis of the Scale during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target Dragon for as long as you control Hivis and Hivis remains tapped.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.WhileControlled), SourceTappedCondition.instance,
                "gain control of target Dragon for as long as you control {this} and {this} remains tapped"
        ), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterDragon));
        this.addAbility(ability);
    }

    private HivisOfTheScale(final HivisOfTheScale card) {
        super(card);
    }

    @Override
    public HivisOfTheScale copy() {
        return new HivisOfTheScale(this);
    }
}
