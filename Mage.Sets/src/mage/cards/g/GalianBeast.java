package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalianBeast extends CardImpl {

    public GalianBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.nightCard = true;
        this.color.setBlack(true);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Galian Beast dies, return it to the battlefield tapped.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true)
                .setText("return it to the battlefield tapped")));
    }

    private GalianBeast(final GalianBeast card) {
        super(card);
    }

    @Override
    public GalianBeast copy() {
        return new GalianBeast(this);
    }
}
