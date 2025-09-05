package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenStrike extends CardImpl {

    public SuddenStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private SuddenStrike(final SuddenStrike card) {
        super(card);
    }

    @Override
    public SuddenStrike copy() {
        return new SuddenStrike(this);
    }
}
