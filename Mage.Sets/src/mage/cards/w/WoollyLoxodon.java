
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WoollyLoxodon extends CardImpl {

    public WoollyLoxodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Morph 5G
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{G}")));
    }

    private WoollyLoxodon(final WoollyLoxodon card) {
        super(card);
    }

    @Override
    public WoollyLoxodon copy() {
        return new WoollyLoxodon(this);
    }
}
