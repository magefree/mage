package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DunlandCrebain extends CardImpl {

    public DunlandCrebain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dunland Crebain enters the battlefield, amass Orcs 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(2, SubType.ORC)));
    }

    private DunlandCrebain(final DunlandCrebain card) {
        super(card);
    }

    @Override
    public DunlandCrebain copy() {
        return new DunlandCrebain(this);
    }
}
