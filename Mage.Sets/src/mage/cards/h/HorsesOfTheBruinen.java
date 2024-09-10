package mage.cards.h;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HorsesOfTheBruinen extends CardImpl {

    public HorsesOfTheBruinen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return up to two target creatures to their owners' hands. Scry 1. The Ring tempts you.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private HorsesOfTheBruinen(final HorsesOfTheBruinen card) {
        super(card);
    }

    @Override
    public HorsesOfTheBruinen copy() {
        return new HorsesOfTheBruinen(this);
    }
}
