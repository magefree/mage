package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author muz
 */
public final class Sear extends CardImpl {

    public Sear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Excavation Explosion deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private Sear(final Sear card) {
        super(card);
    }

    @Override
    public Sear copy() {
        return new Sear(this);
    }
}
