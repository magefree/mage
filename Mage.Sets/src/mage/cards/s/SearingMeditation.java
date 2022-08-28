
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class SearingMeditation extends CardImpl {

    public SearingMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{W}");

        // Whenever you gain life, you may pay {2}. If you do, Searing Meditation deals 2 damage to any target.
        Ability ability = new GainLifeControllerTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(2)), false, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SearingMeditation(final SearingMeditation card) {
        super(card);
    }

    @Override
    public SearingMeditation copy() {
        return new SearingMeditation(this);
    }
}
