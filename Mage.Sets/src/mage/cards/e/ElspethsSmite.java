package mage.cards.e;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethsSmite extends CardImpl {

    public ElspethsSmite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Elspeth's Smite deals 3 damage to target attacking or blocking creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
    }

    private ElspethsSmite(final ElspethsSmite card) {
        super(card);
    }

    @Override
    public ElspethsSmite copy() {
        return new ElspethsSmite(this);
    }
}
