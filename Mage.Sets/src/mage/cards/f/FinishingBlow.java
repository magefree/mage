package mage.cards.f;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinishingBlow extends CardImpl {

    public FinishingBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FinishingBlow(final FinishingBlow card) {
        super(card);
    }

    @Override
    public FinishingBlow copy() {
        return new FinishingBlow(this);
    }
}
