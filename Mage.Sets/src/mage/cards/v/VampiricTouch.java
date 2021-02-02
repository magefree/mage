
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class VampiricTouch extends CardImpl {

    public VampiricTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Vampiric Touch deals 2 damage to target opponent and you gain 2 life.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to target opponent or planeswalker");
        this.getSpellAbility().addEffect(effect);
        effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private VampiricTouch(final VampiricTouch card) {
        super(card);
    }

    @Override
    public VampiricTouch copy() {
        return new VampiricTouch(this);
    }
}
