package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Octoprophet extends CardImpl {

    public Octoprophet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Octoprophet enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));
    }

    private Octoprophet(final Octoprophet card) {
        super(card);
    }

    @Override
    public Octoprophet copy() {
        return new Octoprophet(this);
    }
}
