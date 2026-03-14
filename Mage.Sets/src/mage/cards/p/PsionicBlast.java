package mage.cards.p;

import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PsionicBlast extends CardImpl {

    public PsionicBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Psionic Blast deals 4 damage to any target and 2 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetAndYouEffect(4, 2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private PsionicBlast(final PsionicBlast card) {
        super(card);
    }

    @Override
    public PsionicBlast copy() {
        return new PsionicBlast(this);
    }
}
