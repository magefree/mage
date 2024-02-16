
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MalevolentWhispers extends CardImpl {

    public MalevolentWhispers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gets +2/+0 and gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        this.getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("It gets +2/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains haste until end of turn");
        this.getSpellAbility().addEffect(effect);

        // Madness {3}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{3}{R}")));
    }

    private MalevolentWhispers(final MalevolentWhispers card) {
        super(card);
    }

    @Override
    public MalevolentWhispers copy() {
        return new MalevolentWhispers(this);
    }
}
