    
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class WirewoodSavage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Beast");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }

    public WirewoodSavage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Beast enters the battlefield, you may draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, true));
    }

    private WirewoodSavage(final WirewoodSavage card) {
        super(card);
    }

    @Override
    public WirewoodSavage copy() {
        return new WirewoodSavage(this);
    }
}
