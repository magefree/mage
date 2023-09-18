
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class SunfireBalm extends CardImpl {

    public SunfireBalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Prevent the next 4 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Cycling {1}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{W}")));
        // When you cycle Sunfire Balm, you may prevent the next 1 damage that would be dealt to any target this turn.
        Ability ability = new CycleTriggeredAbility(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private SunfireBalm(final SunfireBalm card) {
        super(card);
    }

    @Override
    public SunfireBalm copy() {
        return new SunfireBalm(this);
    }
}
