
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BorrowedHostility extends CardImpl {

    private static final FilterCreaturePermanent filterBoost = new FilterCreaturePermanent("creature to get +3/+0");
    private static final FilterCreaturePermanent filterFirstStrike = new FilterCreaturePermanent("creature to gain first strike");

    public BorrowedHostility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Escalate {3}
        this.addAbility(new EscalateAbility(new ManaCostsImpl<>("{3}")));

        // Choose one or both &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Target creature gets +3/+0 until end of turn.;
        Effect effect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+0 until end of turn");
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterBoost));
        this.getSpellAbility().addEffect(effect);

        // Target creature gains first strike until end of turn.
        Mode mode = new Mode();
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target creature gains first strike until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterFirstStrike));
        this.getSpellAbility().addMode(mode);
    }

    public BorrowedHostility(final BorrowedHostility card) {
        super(card);
    }

    @Override
    public BorrowedHostility copy() {
        return new BorrowedHostility(this);
    }
}
