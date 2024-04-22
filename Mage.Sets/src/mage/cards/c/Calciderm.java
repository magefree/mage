package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Calciderm extends CardImpl {

    public Calciderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // Vanishing 4
        this.addAbility(new VanishingAbility(4));
    }

    private Calciderm(final Calciderm card) {
        super(card);
    }

    @Override
    public Calciderm copy() {
        return new Calciderm(this);
    }
}
