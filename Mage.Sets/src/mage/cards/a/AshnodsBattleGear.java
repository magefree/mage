
package mage.cards.a;

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
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author LoneFox
 */
public final class AshnodsBattleGear extends CardImpl {

    public AshnodsBattleGear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // You may choose not to untap Ashnod's Battle Gear during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}, {tap}: Target creature you control gets +2/-2 for as long as Ashnod's Battle Gear remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostTargetEffect(2, -2, Duration.Custom), SourceTappedCondition.TAPPED,
                "target creature you control gets +2/-2 for as long as {this} remains tapped"), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AshnodsBattleGear(final AshnodsBattleGear card) {
        super(card);
    }

    @Override
    public AshnodsBattleGear copy() {
        return new AshnodsBattleGear(this);
    }
}
