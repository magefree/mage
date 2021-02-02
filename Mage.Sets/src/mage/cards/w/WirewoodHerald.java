
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class WirewoodHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elf card");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public WirewoodHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Wirewood Herald dies, you may search your library for an Elf card, reveal that card, put it into your hand, then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true),
                true));
    }

    private WirewoodHerald(final WirewoodHerald card) {
        super(card);
    }

    @Override
    public WirewoodHerald copy() {
        return new WirewoodHerald(this);
    }
}
