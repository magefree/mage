
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Archer262
 */
public final class ActOfHeroism extends CardImpl {

    public ActOfHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Untap target creature.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature");
        this.getSpellAbility().addEffect(effect);

        // It gets +2/+2 until end of turn
        effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("It gets +2/+2 until end of turn");
        this.getSpellAbility().addEffect(effect);

        // and can block an additional creature this turn
        effect = new CanBlockAdditionalCreatureTargetEffect();
        effect.setText("and can block an additional creature this turn.");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ActOfHeroism(final ActOfHeroism card) {
        super(card);
    }

    @Override
    public ActOfHeroism copy() {
        return new ActOfHeroism(this);
    }
}
