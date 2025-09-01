package mage.cards.s;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Symbiosis extends CardImpl {

    public Symbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Two target creatures each get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private Symbiosis(final Symbiosis card) {
        super(card);
    }

    @Override
    public Symbiosis copy() {
        return new Symbiosis(this);
    }
}
