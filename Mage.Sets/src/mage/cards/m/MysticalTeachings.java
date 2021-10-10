
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class MysticalTeachings extends CardImpl {


    private static final FilterCard filter = new FilterCard("an instant card or a card with flash");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                new AbilityPredicate(FlashAbility.class)));
    }

    public MysticalTeachings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Search your library for an instant card or a card with flash, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
        // Flashback {5}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{B}")));
    }

    private MysticalTeachings(final MysticalTeachings card) {
        super(card);
    }

    @Override
    public MysticalTeachings copy() {
        return new MysticalTeachings(this);
    }
}
