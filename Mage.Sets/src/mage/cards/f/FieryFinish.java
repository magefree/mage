package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FieryFinish extends CardImpl {

    public FieryFinish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Fiery Finish deals 7 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FieryFinish(final FieryFinish card) {
        super(card);
    }

    @Override
    public FieryFinish copy() {
        return new FieryFinish(this);
    }
}
