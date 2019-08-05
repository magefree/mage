
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author markedagain
 */
public final class SproutingPhytohydra extends CardImpl {

    public SproutingPhytohydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Sprouting Phytohydra is dealt damage, you may create a token that's a copy of Sprouting Phytohydra.
        Effect effect = new CreateTokenCopySourceEffect();
        effect.setText("you may create a token that's a copy of {this}");
        this.addAbility(new DealtDamageToSourceTriggeredAbility(effect, true));
    }

    public SproutingPhytohydra(final SproutingPhytohydra card) {
        super(card);
    }

    @Override
    public SproutingPhytohydra copy() {
        return new SproutingPhytohydra(this);
    }
}
