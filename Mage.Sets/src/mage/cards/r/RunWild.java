
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class RunWild extends CardImpl {

    public RunWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Until end of turn, target creature gains trample and "{G}: Regenerate this creature."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect1 = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect1.setText("Until end of turn, target creature gains trample");
        this.getSpellAbility().addEffect(effect1);

        Effect effect2 = new GainAbilityTargetEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")), Duration.EndOfTurn);
        effect2.setText("and \"{G}: Regenerate this creature\"");
        this.getSpellAbility().addEffect(effect2);

    }

    private RunWild(final RunWild card) {
        super(card);
    }

    @Override
    public RunWild copy() {
        return new RunWild(this);
    }
}
