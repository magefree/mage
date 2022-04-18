package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class Antagonize extends CardImpl {

    public Antagonize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +4/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Antagonize(final Antagonize card) {
        super(card);
    }

    @Override
    public Antagonize copy() {
        return new Antagonize(this);
    }
}
