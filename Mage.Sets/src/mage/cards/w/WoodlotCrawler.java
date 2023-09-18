

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WoodlotCrawler extends CardImpl {

    public WoodlotCrawler (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));

    }

    private WoodlotCrawler(final WoodlotCrawler card) {
        super(card);
    }

    @Override
    public WoodlotCrawler copy() {
        return new WoodlotCrawler(this);
    }

}
