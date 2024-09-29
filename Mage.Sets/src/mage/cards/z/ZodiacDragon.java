package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ZodiacDragon extends CardImpl {

    public ZodiacDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Zodiac Dragon is put into your graveyard from the battlefield, you may return it to your hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()
                .setText("return it to your hand"), true, true));
    }

    private ZodiacDragon(final ZodiacDragon card) {
        super(card);
    }

    @Override
    public ZodiacDragon copy() {
        return new ZodiacDragon(this);
    }
}
