
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SpittingGourna extends CardImpl {

    public SpittingGourna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Morph {4}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{G}")));
    }

    private SpittingGourna(final SpittingGourna card) {
        super(card);
    }

    @Override
    public SpittingGourna copy() {
        return new SpittingGourna(this);
    }
}
