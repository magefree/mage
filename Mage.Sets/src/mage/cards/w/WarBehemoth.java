
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
public final class WarBehemoth extends CardImpl {

    public WarBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Morph {4}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{W}")));
    }

    private WarBehemoth(final WarBehemoth card) {
        super(card);
    }

    @Override
    public WarBehemoth copy() {
        return new WarBehemoth(this);
    }
}
