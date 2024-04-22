
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class LilianasShade extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Swamp card");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public LilianasShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Liliana's Shade enters the battlefield, you may search your library for a Swamp card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true));
        // {B}: Liliana's Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private LilianasShade(final LilianasShade card) {
        super(card);
    }

    @Override
    public LilianasShade copy() {
        return new LilianasShade(this);
    }
}
