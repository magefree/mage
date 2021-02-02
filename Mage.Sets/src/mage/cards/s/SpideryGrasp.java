
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class SpideryGrasp extends CardImpl {

    public SpideryGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Untap target creature. It gets +2/+4 and gains reach until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        Effect effect = new BoostTargetEffect(2, 4, Duration.EndOfTurn);
        effect.setText("It gets +2/+4");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach until end of turn");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SpideryGrasp(final SpideryGrasp card) {
        super(card);
    }

    @Override
    public SpideryGrasp copy() {
        return new SpideryGrasp(this);
    }
}
