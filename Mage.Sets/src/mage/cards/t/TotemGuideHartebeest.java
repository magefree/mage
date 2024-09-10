
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class TotemGuideHartebeest extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura card");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(SubType.AURA.getPredicate());
    }

    public TotemGuideHartebeest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ANTELOPE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Totem-Guide Hartebeest enters the battlefield, you may search your library for an Aura card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true));
    }

    private TotemGuideHartebeest(final TotemGuideHartebeest card) {
        super(card);
    }

    @Override
    public TotemGuideHartebeest copy() {
        return new TotemGuideHartebeest(this);
    }
}
