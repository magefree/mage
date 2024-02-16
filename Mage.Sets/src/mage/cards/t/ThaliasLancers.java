
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class ThaliasLancers extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ThaliasLancers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Thalia's Lancers enters the battlefield, you may search your library for a legendary card, reveal it, put it into your hand, then shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, filter), true);
        effect.setText("you may search your library for a legendary card, reveal it, put it into your hand, then shuffle");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));
    }

    private ThaliasLancers(final ThaliasLancers card) {
        super(card);
    }

    @Override
    public ThaliasLancers copy() {
        return new ThaliasLancers(this);
    }
}
