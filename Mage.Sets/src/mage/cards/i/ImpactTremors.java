
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ImpactTremors extends CardImpl {

    public ImpactTremors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // Whenever a creature enters the battlefield under your control, Impact Tremors deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new DamagePlayersEffect(Outcome.Damage, StaticValue.get(1), TargetController.OPPONENT),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false));
    }

    private ImpactTremors(final ImpactTremors card) {
        super(card);
    }

    @Override
    public ImpactTremors copy() {
        return new ImpactTremors(this);
    }
}
