
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class HedronCrawler extends CardImpl {

    public HedronCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private HedronCrawler(final HedronCrawler card) {
        super(card);
    }

    @Override
    public HedronCrawler copy() {
        return new HedronCrawler(this);
    }
}
