package mage.cards.b;

import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Boltwave extends CardImpl {

    public Boltwave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Boltwave deals 3 damage to each opponent.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT));
    }

    private Boltwave(final Boltwave card) {
        super(card);
    }

    @Override
    public Boltwave copy() {
        return new Boltwave(this);
    }
}
