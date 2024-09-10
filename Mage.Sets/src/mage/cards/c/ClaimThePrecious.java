package mage.cards.c;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClaimThePrecious extends CardImpl {

    public ClaimThePrecious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Destroy target creature. The Ring tempts you.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private ClaimThePrecious(final ClaimThePrecious card) {
        super(card);
    }

    @Override
    public ClaimThePrecious copy() {
        return new ClaimThePrecious(this);
    }
}
