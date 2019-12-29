package mage.cards.e;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EngulfingEruption extends CardImpl {

    public EngulfingEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Engulfing Eruption deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EngulfingEruption(final EngulfingEruption card) {
        super(card);
    }

    @Override
    public EngulfingEruption copy() {
        return new EngulfingEruption(this);
    }
}
