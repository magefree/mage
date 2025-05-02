
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ViciousHunger extends CardImpl {

    public ViciousHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Vicious Hunger deals 2 damage to target creature and you gain 2 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    private ViciousHunger(final ViciousHunger card) {
        super(card);
    }

    @Override
    public ViciousHunger copy() {
        return new ViciousHunger(this);
    }
}
