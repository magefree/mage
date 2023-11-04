package mage.cards.c;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmiumBlast extends CardImpl {

    public CosmiumBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cosmium Blast deals 4 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private CosmiumBlast(final CosmiumBlast card) {
        super(card);
    }

    @Override
    public CosmiumBlast copy() {
        return new CosmiumBlast(this);
    }
}
