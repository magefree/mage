package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Scorchmark extends CardImpl {

    public Scorchmark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Scorchmark deals 2 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Scorchmark(final Scorchmark card) {
        super(card);
    }

    @Override
    public Scorchmark copy() {
        return new Scorchmark(this);
    }
}
