
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PharikasCure extends CardImpl {

    public PharikasCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");


        // Pharika's Cure deals 2 damage to target creature and you gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    private PharikasCure(final PharikasCure card) {
        super(card);
    }

    @Override
    public PharikasCure copy() {
        return new PharikasCure(this);
    }
}
