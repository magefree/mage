
package mage.cards.e;

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
import mage.abilities.keyword.TrampleAbility;
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
public final class EvergloveCourier extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creature");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public EvergloveCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may choose not to untap Everglove Courier during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}{G}, {tap}: Target Elf creature gets +2/+2 and has trample for as long as Everglove Courier remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(2, 2, Duration.Custom), SourceTappedCondition.TAPPED,
            "target Elf creature gets +2/+2"), new ManaCostsImpl<>("{2}{G}"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(),
            Duration.Custom), SourceTappedCondition.TAPPED,"and has trample for as long as {this} remains tapped"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private EvergloveCourier(final EvergloveCourier card) {
        super(card);
    }

    @Override
    public EvergloveCourier copy() {
        return new EvergloveCourier(this);
    }
}
