
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class RevengeOfTheHunted extends CardImpl {

    public RevengeOfTheHunted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}{G}");


        // Until end of turn, target creature gets +6/+6 and gains trample, and all creatures able to block it this turn do so.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(6, 6, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        Effect effect = new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn);
        effect.setText("and all creatures able to block it this turn do so");
        this.getSpellAbility().addEffect(effect);

        // Miracle {G}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{G}")));
    }

    private RevengeOfTheHunted(final RevengeOfTheHunted card) {
        super(card);
    }

    @Override
    public RevengeOfTheHunted copy() {
        return new RevengeOfTheHunted(this);
    }
}
