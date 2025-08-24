package mage.cards.j;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class JaggedLightning extends CardImpl {

    public JaggedLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Jagged Lightning deals 3 damage to each of two target creatures.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3, true, "each of two target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private JaggedLightning(final JaggedLightning card) {
        super(card);
    }

    @Override
    public JaggedLightning copy() {
        return new JaggedLightning(this);
    }
}
