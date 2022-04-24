
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class WallOfJunk extends CardImpl {

    public WallOfJunk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // When Wall of Junk blocks, return it to its owner's hand at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)));
        effect.setText("return it to its owner's hand at end of combat");
        this.addAbility(new BlocksSourceTriggeredAbility(effect).setTriggerPhrase("When {this} blocks, "));
    }

    private WallOfJunk(final WallOfJunk card) {
        super(card);
    }

    @Override
    public WallOfJunk copy() {
        return new WallOfJunk(this);
    }
}
