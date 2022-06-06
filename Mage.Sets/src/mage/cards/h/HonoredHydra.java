
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class HonoredHydra extends CardImpl {

    public HonoredHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Embalm {3}{G}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{G}"), this));

    }

    private HonoredHydra(final HonoredHydra card) {
        super(card);
    }

    @Override
    public HonoredHydra copy() {
        return new HonoredHydra(this);
    }
}
