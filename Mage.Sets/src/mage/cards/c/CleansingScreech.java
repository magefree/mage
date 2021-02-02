package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class CleansingScreech extends CardImpl {

    public CleansingScreech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Cleansing Screech deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private CleansingScreech(final CleansingScreech card) {
        super(card);
    }

    @Override
    public CleansingScreech copy() {
        return new CleansingScreech(this);
    }
}
