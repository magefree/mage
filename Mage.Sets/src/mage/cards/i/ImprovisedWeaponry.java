package mage.cards.i;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImprovisedWeaponry extends CardImpl {

    public ImprovisedWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Improvised Weaponry deals 2 damage to any target. Create a Treasure token.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("."));
    }

    private ImprovisedWeaponry(final ImprovisedWeaponry card) {
        super(card);
    }

    @Override
    public ImprovisedWeaponry copy() {
        return new ImprovisedWeaponry(this);
    }
}
