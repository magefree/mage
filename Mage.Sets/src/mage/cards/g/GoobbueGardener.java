package mage.cards.g;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoobbueGardener extends CardImpl {

    public GoobbueGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private GoobbueGardener(final GoobbueGardener card) {
        super(card);
    }

    @Override
    public GoobbueGardener copy() {
        return new GoobbueGardener(this);
    }
}
