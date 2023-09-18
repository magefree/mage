package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Strangle extends CardImpl {

    public Strangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Strangle deals 3 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private Strangle(final Strangle card) {
        super(card);
    }

    @Override
    public Strangle copy() {
        return new Strangle(this);
    }
}
