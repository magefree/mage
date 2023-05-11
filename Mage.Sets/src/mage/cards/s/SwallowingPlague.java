

package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class SwallowingPlague extends CardImpl {

    public SwallowingPlague (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SwallowingPlague (final SwallowingPlague card) {
        super(card);
    }

    @Override
    public SwallowingPlague copy() {
        return new SwallowingPlague(this);
    }

}
