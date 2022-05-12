
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class TeferisVeil extends CardImpl {

    public TeferisVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever a creature you control attacks, it phases out at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new PhaseOutTargetEffect("it")));
        effect.setText("it phases out at end of combat");
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(effect, false, true));
    }

    private TeferisVeil(final TeferisVeil card) {
        super(card);
    }

    @Override
    public TeferisVeil copy() {
        return new TeferisVeil(this);
    }
}
