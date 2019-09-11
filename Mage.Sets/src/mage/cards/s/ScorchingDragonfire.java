package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorchingDragonfire extends CardImpl {

    public ScorchingDragonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Scorching Dragonfire deals 3 damage to target creature or planeswalker. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect()
                .setText("If that creature or planeswalker would die this turn, exile it instead."));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ScorchingDragonfire(final ScorchingDragonfire card) {
        super(card);
    }

    @Override
    public ScorchingDragonfire copy() {
        return new ScorchingDragonfire(this);
    }
}
