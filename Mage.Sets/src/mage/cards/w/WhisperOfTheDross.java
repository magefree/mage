package mage.cards.w;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhisperOfTheDross extends CardImpl {

    public WhisperOfTheDross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -1/-1 until end of turn. Proliferate.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private WhisperOfTheDross(final WhisperOfTheDross card) {
        super(card);
    }

    @Override
    public WhisperOfTheDross copy() {
        return new WhisperOfTheDross(this);
    }
}
