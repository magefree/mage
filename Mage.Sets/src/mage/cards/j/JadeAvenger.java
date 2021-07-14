package mage.cards.j;

import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadeAvenger extends CardImpl {

    public JadeAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 2
        this.addAbility(new BushidoAbility(2));
    }

    private JadeAvenger(final JadeAvenger card) {
        super(card);
    }

    @Override
    public JadeAvenger copy() {
        return new JadeAvenger(this);
    }
}
