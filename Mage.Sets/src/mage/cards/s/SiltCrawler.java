
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class SiltCrawler extends CardImpl {

    public SiltCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Silt Crawler enters the battlefield, tap all lands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(new FilterControlledLandPermanent("lands you control")), false));
    }

    private SiltCrawler(final SiltCrawler card) {
        super(card);
    }

    @Override
    public SiltCrawler copy() {
        return new SiltCrawler(this);
    }
}
