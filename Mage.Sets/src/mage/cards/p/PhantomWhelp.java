
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes & L_J
 */
public final class PhantomWhelp extends CardImpl {

    public PhantomWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Phantom Whelp attacks or blocks, return it to its owner's hand at end of combat. (Return it only if it's on the battlefield.)
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)));
        effect.setText("return it to its owner's hand at end of combat");
        this.addAbility(new AttacksOrBlocksTriggeredAbility(effect, false));
    }

    private PhantomWhelp(final PhantomWhelp card) {
        super(card);
    }

    @Override
    public PhantomWhelp copy() {
        return new PhantomWhelp(this);
    }
}
