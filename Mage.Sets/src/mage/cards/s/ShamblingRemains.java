
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ShamblingRemains extends CardImpl {

    public ShamblingRemains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Shambling Remains can't block.
        this.addAbility(new CantBlockAbility());
        // Unearth {B}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{B}{R}")));
    }

    private ShamblingRemains(final ShamblingRemains card) {
        super(card);
    }

    @Override
    public ShamblingRemains copy() {
        return new ShamblingRemains(this);
    }
}
