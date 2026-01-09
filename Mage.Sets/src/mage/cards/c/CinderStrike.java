package mage.cards.c;

import mage.abilities.condition.common.BlightedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BlightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CinderStrike extends CardImpl {

    public CinderStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast this spell, you may blight 1.
        this.addAbility(new BlightAbility(1));

        // Cinder Strike deals 2 damage to target creature. It deals 4 damage to that creature instead if this spell's additional cost was paid.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(4), new DamageTargetEffect(2),
                BlightedCondition.instance, "{this} deals 2 damage to target creature. " +
                "It deals 4 damage to that creature instead if this spell's additional cost was paid"
        ));
    }

    private CinderStrike(final CinderStrike card) {
        super(card);
    }

    @Override
    public CinderStrike copy() {
        return new CinderStrike(this);
    }
}
