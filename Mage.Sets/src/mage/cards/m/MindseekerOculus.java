package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MindseekerOculus extends CardImpl {

    public MindseekerOculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, empower Jace 4.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(4)));
    }

    private MindseekerOculus(final MindseekerOculus card) {
        super(card);
    }

    @Override
    public MindseekerOculus copy() {
        return new MindseekerOculus(this);
    }
}
