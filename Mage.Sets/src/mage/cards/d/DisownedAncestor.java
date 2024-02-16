
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DisownedAncestor extends CardImpl {

    public DisownedAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Outlast {1}{B}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private DisownedAncestor(final DisownedAncestor card) {
        super(card);
    }

    @Override
    public DisownedAncestor copy() {
        return new DisownedAncestor(this);
    }
}
