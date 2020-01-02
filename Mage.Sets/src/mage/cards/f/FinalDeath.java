package mage.cards.f;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinalDeath extends CardImpl {

    public FinalDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FinalDeath(final FinalDeath card) {
        super(card);
    }

    @Override
    public FinalDeath copy() {
        return new FinalDeath(this);
    }
}
