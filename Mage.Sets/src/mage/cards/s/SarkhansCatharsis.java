package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarkhansCatharsis extends CardImpl {

    public SarkhansCatharsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Sarkhan's Catharsis deals 5 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private SarkhansCatharsis(final SarkhansCatharsis card) {
        super(card);
    }

    @Override
    public SarkhansCatharsis copy() {
        return new SarkhansCatharsis(this);
    }
}
