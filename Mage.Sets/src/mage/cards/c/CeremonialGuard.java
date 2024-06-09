
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class CeremonialGuard extends CardImpl {

    public CeremonialGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Ceremonial Guard attacks or blocks, destroy it at end of combat.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new DestroySourceEffect().setText("destroy it at end of combat"))
                                .setTriggerPhrase("")),
                false));
    }

    private CeremonialGuard(final CeremonialGuard card) {
        super(card);
    }

    @Override
    public CeremonialGuard copy() {
        return new CeremonialGuard(this);
    }
}
