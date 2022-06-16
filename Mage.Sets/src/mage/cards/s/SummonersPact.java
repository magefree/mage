
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class SummonersPact extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("green creature card");
    static{
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }
    public SummonersPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{0}");

        this.color.setGreen(true);
        
        // Search your library for a green creature card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
        // At the beginning of your next upkeep, pay {2}{G}{G}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl<>("{2}{G}{G}"))));
    }

    private SummonersPact(final SummonersPact card) {
        super(card);
    }

    @Override
    public SummonersPact copy() {
        return new SummonersPact(this);
    }
}
