
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShapeTheSands extends CardImpl {

    public ShapeTheSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +0/+5 and gains reach until end of turn.
        Effect effect = new BoostTargetEffect(0, 5, Duration.EndOfTurn);
        effect.setText("Target creature gets +0/+5");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private ShapeTheSands(final ShapeTheSands card) {
        super(card);
    }

    @Override
    public ShapeTheSands copy() {
        return new ShapeTheSands(this);
    }
}
