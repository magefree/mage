package mage.cards.f;

import java.util.UUID;

import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author TheElk801
 */
public final class ForkedLightning extends CardImpl {

    public ForkedLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Forked Lightning deals 4 damage divided as you choose among one, two, or three target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4)
                .setText("{this} deals 4 damage divided as you choose among one, two, or three target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4, 1, 3));
    }

    private ForkedLightning(final ForkedLightning card) {
        super(card);
    }

    @Override
    public ForkedLightning copy() {
        return new ForkedLightning(this);
    }
}
