package mage.cards.f;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ForkedBolt extends CardImpl {

    public ForkedBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Forked Bolt deals 2 damage divided as you choose among one or two target creatures and/or players.
        Effect effect = new DamageMultiEffect(2);
        effect.setText("{this} deals 2 damage divided as you choose among one or two target creatures and/or players");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(2));
    }

    private ForkedBolt(final ForkedBolt card) {
        super(card);
    }

    @Override
    public ForkedBolt copy() {
        return new ForkedBolt(this);
    }
}
