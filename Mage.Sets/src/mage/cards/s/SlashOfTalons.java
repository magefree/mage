
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author TheElk801
 */
public final class SlashOfTalons extends CardImpl {

    public SlashOfTalons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Slash of Talons deals 2 damage to target attacking or blocking creature.
        getSpellAbility().addEffect(new DamageTargetEffect(2));
        getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private SlashOfTalons(final SlashOfTalons card) {
        super(card);
    }

    @Override
    public SlashOfTalons copy() {
        return new SlashOfTalons(this);
    }
}
