package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionOfTheUnspeakable extends CardImpl {

    public VisionOfTheUnspeakable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Vision of the Unspeakable gets +1/+1 for each card in your hand.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                CardsInControllerHandCount.instance,
                CardsInControllerHandCount.instance,
                Duration.WhileOnBattlefield
        )));
    }

    private VisionOfTheUnspeakable(final VisionOfTheUnspeakable card) {
        super(card);
    }

    @Override
    public VisionOfTheUnspeakable copy() {
        return new VisionOfTheUnspeakable(this);
    }
}
