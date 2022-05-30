
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public final class DefilingTears extends CardImpl {

    public DefilingTears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Until end of turn, target creature becomes black, gets +1/-1, and gains "{B}: Regenerate this creature."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        Effect effect = new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature becomes black");
        this.getSpellAbility().addEffect(effect);

        effect = new BoostTargetEffect(1, -1, Duration.EndOfTurn);
        effect.setText(", gets +1/-1");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")), Duration.EndOfTurn);
        effect.setText(", and gains \"{B}: Regenerate this creature.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private DefilingTears(final DefilingTears card) {
        super(card);
    }

    @Override
    public DefilingTears copy() {
        return new DefilingTears(this);
    }
}
