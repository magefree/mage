package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntrancingLyre extends CardImpl {

    public EntrancingLyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may choose not to untap Entrancing Lyre during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {X}, {T}: Tap target creature with power X or less. It doesn't untap during its controller's untap step for as long as Entrancing Lyre remains tapped.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect("tap target creature with power X or less"), new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        ability.setTargetAdjuster(new PowerTargetAdjuster(ComparisonType.OR_LESS));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private EntrancingLyre(final EntrancingLyre card) {
        super(card);
    }

    @Override
    public EntrancingLyre copy() {
        return new EntrancingLyre(this);
    }
}
