
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author Loki
 */
public final class PunctureBlast extends CardImpl {

    public PunctureBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        this.addAbility(WitherAbility.getInstance());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().setRuleAtTheTop(false);
    }

    private PunctureBlast(final PunctureBlast card) {
        super(card);
    }

    @Override
    public PunctureBlast copy() {
        return new PunctureBlast(this);
    }
}
