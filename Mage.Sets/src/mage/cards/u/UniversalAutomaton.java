package mage.cards.u;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UniversalAutomaton extends CardImpl {

    public UniversalAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());
    }

    private UniversalAutomaton(final UniversalAutomaton card) {
        super(card);
    }

    @Override
    public UniversalAutomaton copy() {
        return new UniversalAutomaton(this);
    }
}
