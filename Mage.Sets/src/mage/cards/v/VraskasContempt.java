
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author spjspj
 */
public final class VraskasContempt extends CardImpl {

    public VraskasContempt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Exile target creature or planeswalker. You gain 2 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private VraskasContempt(final VraskasContempt card) {
        super(card);
    }

    @Override
    public VraskasContempt copy() {
        return new VraskasContempt(this);
    }
}
