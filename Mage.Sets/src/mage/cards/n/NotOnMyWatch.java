package mage.cards.n;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NotOnMyWatch extends CardImpl {

    public NotOnMyWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target attacking creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private NotOnMyWatch(final NotOnMyWatch card) {
        super(card);
    }

    @Override
    public NotOnMyWatch copy() {
        return new NotOnMyWatch(this);
    }
}
