
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class ResoundingThunder extends CardImpl {

    public ResoundingThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Resounding Thunder deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Cycling {5}{B}{R}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{5}{B}{R}{G}")));
        // When you cycle Resounding Thunder, it deals 6 damage to any target.
        Ability ability = new CycleTriggeredAbility(new DamageTargetEffect(6, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ResoundingThunder(final ResoundingThunder card) {
        super(card);
    }

    @Override
    public ResoundingThunder copy() {
        return new ResoundingThunder(this);
    }
}
