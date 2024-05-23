
package mage.cards.d;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Disembowel extends CardImpl {

    public Disembowel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Destroy target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with mana value X"));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Disembowel(final Disembowel card) {
        super(card);
    }

    @Override
    public Disembowel copy() {
        return new Disembowel(this);
    }
}
