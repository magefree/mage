package mage.cards.m;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagicMissile extends CardImpl {

    public MagicMissile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Magic Missile deals 3 damage divided as you choose among one, two, or three targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(3));
    }

    private MagicMissile(final MagicMissile card) {
        super(card);
    }

    @Override
    public MagicMissile copy() {
        return new MagicMissile(this);
    }
}
