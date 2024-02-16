package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrayMerchantOfAsphodel extends CardImpl {

    public GrayMerchantOfAsphodel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Gray Merchant of Asphodel enters the battlefield, each opponent loses X life, where X is your devotion to black. You gain life equal to the life lost this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsYouGainLifeLostEffect(
                DevotionCount.B, "X life, where X is your devotion to black"), false
        ).addHint(DevotionCount.B.getHint()));
    }

    private GrayMerchantOfAsphodel(final GrayMerchantOfAsphodel card) {
        super(card);
    }

    @Override
    public GrayMerchantOfAsphodel copy() {
        return new GrayMerchantOfAsphodel(this);
    }
}
