
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class ChandrasFury extends CardImpl {

    public ChandrasFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Chandra's Fury deals 4 damage to target player and 1 damage to each creature that player controls.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(1)
                .setText("and 1 damage to each creature that player or that planeswalker's controller controls")
        );
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private ChandrasFury(final ChandrasFury card) {
        super(card);
    }

    @Override
    public ChandrasFury copy() {
        return new ChandrasFury(this);
    }
}
