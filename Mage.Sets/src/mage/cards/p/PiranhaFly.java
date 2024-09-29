package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiranhaFly extends CardImpl {

    public PiranhaFly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Piranha Fly enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private PiranhaFly(final PiranhaFly card) {
        super(card);
    }

    @Override
    public PiranhaFly copy() {
        return new PiranhaFly(this);
    }
}
