package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.permanent.token.ChocoboToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallTheMountainChocobo extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mountain card");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public CallTheMountainChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Search your library for a Mountain card, reveal it, put it into your hand, then shuffle. Create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ChocoboToken()));

        // Flashback {5}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}")));
    }

    private CallTheMountainChocobo(final CallTheMountainChocobo card) {
        super(card);
    }

    @Override
    public CallTheMountainChocobo copy() {
        return new CallTheMountainChocobo(this);
    }
}
