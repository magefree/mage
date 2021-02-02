
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LightkeeperOfEmeria extends CardImpl {

    public LightkeeperOfEmeria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Multikicker (You may pay an additional {W} any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Lightkeeper of Emeria enters the battlefield, you gain 2 life for each time it was kicked.
        Effect effect = new GainLifeEffect(new MultipliedValue(MultikickerCount.instance, 2));
        effect.setText("you gain 2 life for each time it was kicked");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    private LightkeeperOfEmeria(final LightkeeperOfEmeria card) {
        super(card);
    }

    @Override
    public LightkeeperOfEmeria copy() {
        return new LightkeeperOfEmeria(this);
    }
}
