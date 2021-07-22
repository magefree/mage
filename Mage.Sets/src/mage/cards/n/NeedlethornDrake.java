package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeedlethornDrake extends CardImpl {

    public NeedlethornDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private NeedlethornDrake(final NeedlethornDrake card) {
        super(card);
    }

    @Override
    public NeedlethornDrake copy() {
        return new NeedlethornDrake(this);
    }
}
