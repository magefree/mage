
package mage.cards.p;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Wehk
 */
public final class ProfanePrayers extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Cleric on the battlefield");

    static {
        filter.add(SubType.CLERIC.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    
    public ProfanePrayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Profane Prayers deals X damage to any target and you gain X life, where X is the number of Clerics on the battlefield.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue)
                .setText("and you gain X life, where X is the number of Clerics on the battlefield"));
    }

    private ProfanePrayers(final ProfanePrayers card) {
        super(card);
    }

    @Override
    public ProfanePrayers copy() {
        return new ProfanePrayers(this);
    }
}
