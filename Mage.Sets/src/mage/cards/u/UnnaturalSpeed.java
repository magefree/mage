

package mage.cards.u;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class UnnaturalSpeed extends CardImpl {

    public UnnaturalSpeed (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnnaturalSpeed(final UnnaturalSpeed card) {
        super(card);
    }

    @Override
    public UnnaturalSpeed copy() {
        return new UnnaturalSpeed(this);
    }

}
