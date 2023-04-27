
package mage.cards.s;

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
 * @author LevelX2
 */
public final class SolarBlast extends CardImpl {

    public SolarBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Solar Blast deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Cycling {1}{R}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}{R}")));
        // When you cycle Solar Blast, you may have it deal 1 damage to any target.
        Ability ability = new CycleTriggeredAbility(new DamageTargetEffect(1), true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SolarBlast(final SolarBlast card) {
        super(card);
    }

    @Override
    public SolarBlast copy() {
        return new SolarBlast(this);
    }
}
