package mage.cards.e;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElephantRat extends CardImpl {

    public ElephantRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private ElephantRat(final ElephantRat card) {
        super(card);
    }

    @Override
    public ElephantRat copy() {
        return new ElephantRat(this);
    }
}
