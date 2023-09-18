
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class InTheWebOfWar extends CardImpl {

    public InTheWebOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Whenever a creature enters the battlefield under your control, it gets +2/+0 and gains haste until end of turn.
        Effect effect = new BoostTargetEffect(2,0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0");
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_PERMANENT_A_CREATURE, false, SetTargetPointer.PERMANENT, null);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains haste until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private InTheWebOfWar(final InTheWebOfWar card) {
        super(card);
    }

    @Override
    public InTheWebOfWar copy() {
        return new InTheWebOfWar(this);
    }
}
