package mage.cards.i;

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
public final class ImpostorOfTheSixthPride extends CardImpl {

    public ImpostorOfTheSixthPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());
    }

    private ImpostorOfTheSixthPride(final ImpostorOfTheSixthPride card) {
        super(card);
    }

    @Override
    public ImpostorOfTheSixthPride copy() {
        return new ImpostorOfTheSixthPride(this);
    }
}
