package mage.cards.w;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderersIntervention extends CardImpl {

    public WanderersIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Wanderer's Intervention deals 4 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private WanderersIntervention(final WanderersIntervention card) {
        super(card);
    }

    @Override
    public WanderersIntervention copy() {
        return new WanderersIntervention(this);
    }
}
