package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class TheMassiveCritter extends CardImpl {

    public TheMassiveCritter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private TheMassiveCritter(final TheMassiveCritter card) {
        super(card);
    }

    @Override
    public TheMassiveCritter copy() {
        return new TheMassiveCritter(this);
    }
}