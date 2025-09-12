package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheTerrorOfSerpentsPass extends CardImpl {

    public TheTerrorOfSerpentsPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private TheTerrorOfSerpentsPass(final TheTerrorOfSerpentsPass card) {
        super(card);
    }

    @Override
    public TheTerrorOfSerpentsPass copy() {
        return new TheTerrorOfSerpentsPass(this);
    }
}
