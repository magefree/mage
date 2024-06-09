
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class ElvishHarbinger extends CardImpl {

    static final FilterCard filter = new FilterCard("Elf card");
    static {
        filter.add(SubType.ELF.getPredicate());
    }
    public ElvishHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Elvish Harbinger enters the battlefield, you may search your library for an Elf card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true));
        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapSourceCost()));
    }

    private ElvishHarbinger(final ElvishHarbinger card) {
        super(card);
    }

    @Override
    public ElvishHarbinger copy() {
        return new ElvishHarbinger(this);
    }
}
