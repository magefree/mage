package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzAutomaton extends CardImpl {

    public BlitzAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Prototype {2}{R} - 3/2
        this.addAbility(new PrototypeAbility(this, "{2}{R}", 3, 2));

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private BlitzAutomaton(final BlitzAutomaton card) {
        super(card);
    }

    @Override
    public BlitzAutomaton copy() {
        return new BlitzAutomaton(this);
    }
}
