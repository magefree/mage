package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SheepWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BridledBighorn extends CardImpl {

    public BridledBighorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SHEEP);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Bridled Bighorn attacks while saddled, create a 1/1 white Sheep creature token.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new CreateTokenEffect(new SheepWhiteToken())));

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private BridledBighorn(final BridledBighorn card) {
        super(card);
    }

    @Override
    public BridledBighorn copy() {
        return new BridledBighorn(this);
    }
}
