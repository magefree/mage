
package mage.cards.z;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ZelyonSword extends CardImpl {

    public ZelyonSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may choose not to untap Zelyon Sword during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {3}, {tap}: Target creature gets +2/+0 for as long as Zelyon Sword remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(2, 0, Duration.Custom), SourceTappedCondition.TAPPED,
            "target creature gets +2/+0 for as long as {this} remains tapped"), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ZelyonSword(final ZelyonSword card) {
        super(card);
    }

    @Override
    public ZelyonSword copy() {
        return new ZelyonSword(this);
    }
}
