package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RetreatToHagra extends CardImpl {

    public RetreatToHagra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, 
        // choose one - Target creature gets +1/+0 and gains deathtouch until end of turn;
        LandfallAbility ability = new LandfallAbility(new BoostTargetEffect(1, 0, Duration.EndOfTurn).setText("target creature gets +1/+0"), false);
        Effect effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn).setText("and gains deathtouch until end of turn");
        effect.setOutcome(Outcome.Benefit);
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent());

        // or Each opponent loses 1 life and you gain 1 life.
        Mode mode = new Mode(new LoseLifeOpponentsEffect(1));
        Effect gainLife = new GainLifeEffect(1);
        gainLife.setText("and you gain 1 life");
        mode.addEffect(gainLife);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RetreatToHagra(final RetreatToHagra card) {
        super(card);
    }

    @Override
    public RetreatToHagra copy() {
        return new RetreatToHagra(this);
    }
}
