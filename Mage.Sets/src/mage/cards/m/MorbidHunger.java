
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author cbt33
 */
public final class MorbidHunger extends CardImpl {

    public MorbidHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Morbid Hunger deals 3 damage to any target. You gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        // Flashback {7}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{7}{B}{B}")));
        
    }

    private MorbidHunger(final MorbidHunger card) {
        super(card);
    }

    @Override
    public MorbidHunger copy() {
        return new MorbidHunger(this);
    }
}
