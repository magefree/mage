package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScornEffigy extends CardImpl {

    public ScornEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Foretell {0}
        this.addAbility(new ForetellAbility(this, "{0}"));
    }

    private ScornEffigy(final ScornEffigy card) {
        super(card);
    }

    @Override
    public ScornEffigy copy() {
        return new ScornEffigy(this);
    }
}
