package mage.cards.d;

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
public final class DragonsPresence extends CardImpl {

    public DragonsPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Dragon's Presence deals 5 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private DragonsPresence(final DragonsPresence card) {
        super(card);
    }

    @Override
    public DragonsPresence copy() {
        return new DragonsPresence(this);
    }
}
