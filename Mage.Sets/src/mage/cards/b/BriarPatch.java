
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class BriarPatch extends CardImpl {

    public BriarPatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a creature attacks you, it gets -1/-0 until end of turn.
        Effect effect = new BoostTargetEffect(-1, 0, Duration.EndOfTurn);
        effect.setText("it gets -1/-0");
        Ability ability = new AttackedByCreatureTriggeredAbility(Zone.BATTLEFIELD, effect, false, SetTargetPointer.PERMANENT);
        addAbility(ability);
    }

    private BriarPatch(final BriarPatch card) {
        super(card);
    }

    @Override
    public BriarPatch copy() {
        return new BriarPatch(this);
    }
}
