
package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.token.TreasureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Hiddevb
 */
public final class Goldhound extends CardImpl {

    public Goldhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.TREASURE);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(true));

        // {T}, Sacrifice Goldhound: Add one mana of any color.
        this.addAbility(new TreasureAbility(true));
    }

    private Goldhound(final Goldhound card) {
        super(card);
    }

    @Override
    public Goldhound copy() {
        return new Goldhound(this);
    }
}
