package mage.cards.f;

import java.util.UUID;

import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
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
        Target target=new TargetCreaturePermanentAmount(4);target.setMaxNumberOfTargets(3);this.getSpellAbility().addTarget(target);
    }

    private ForkedLightning(final ForkedLightning card) {
        super(card);
    }

    @Override
    public ForkedLightning copy() {
        return new ForkedLightning(this);
    }
}
