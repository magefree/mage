package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class DirectCurrent extends CardImpl {

    public DirectCurrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Direct Current deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));

    }

    private DirectCurrent(final DirectCurrent card) {
        super(card);
    }

    @Override
    public DirectCurrent copy() {
        return new DirectCurrent(this);
    }
}
