
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class RockshardElemental extends CardImpl {

    public RockshardElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Morph {4}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}{R}")));
    }

    private RockshardElemental(final RockshardElemental card) {
        super(card);
    }

    @Override
    public RockshardElemental copy() {
        return new RockshardElemental(this);
    }
}
