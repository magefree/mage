package mage.cards.t;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderingRebuke extends CardImpl {

    public ThunderingRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Thundering Rebuke deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ThunderingRebuke(final ThunderingRebuke card) {
        super(card);
    }

    @Override
    public ThunderingRebuke copy() {
        return new ThunderingRebuke(this);
    }
}
