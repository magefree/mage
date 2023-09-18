package mage.cards.w;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderersStrike extends CardImpl {

    public WanderersStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Exile target creature, then proliferate.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new ProliferateEffect(true).concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WanderersStrike(final WanderersStrike card) {
        super(card);
    }

    @Override
    public WanderersStrike copy() {
        return new WanderersStrike(this);
    }
}
