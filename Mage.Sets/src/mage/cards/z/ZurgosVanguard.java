package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZurgosVanguard extends CardImpl {

    public ZurgosVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));

        // This creature's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(CreaturesYouControlCount.PLURAL)));
    }

    private ZurgosVanguard(final ZurgosVanguard card) {
        super(card);
    }

    @Override
    public ZurgosVanguard copy() {
        return new ZurgosVanguard(this);
    }
}
