
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LimitsOfSolidarity extends CardImpl {

    public LimitsOfSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap it");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn");
        this.getSpellAbility().addEffect(effect);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private LimitsOfSolidarity(final LimitsOfSolidarity card) {
        super(card);
    }

    @Override
    public LimitsOfSolidarity copy() {
        return new LimitsOfSolidarity(this);
    }
}
