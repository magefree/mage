
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class SaprazzanOutrigger extends CardImpl {

    public SaprazzanOutrigger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        
        // When Saprazzan Outrigger attacks or blocks, put it on top of its owner's library at end of combat.
        DelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(
                new PutOnLibrarySourceEffect(true, "put it on top of its owner's library at end of combat"))
                .setTriggerPhrase("");
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(ability, true), false));
    }

    private SaprazzanOutrigger(final SaprazzanOutrigger card) {
        super(card);
    }

    @Override
    public SaprazzanOutrigger copy() {
        return new SaprazzanOutrigger(this);
    }
}
