package mage.cards.p;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfaneTutor extends CardImpl {

    public ProfaneTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setBlack(true);

        // Suspend 2—{1}{B}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{1}{B}"), this));

        // Search your library for a card, put that card into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));
    }

    private ProfaneTutor(final ProfaneTutor card) {
        super(card);
    }

    @Override
    public ProfaneTutor copy() {
        return new ProfaneTutor(this);
    }
}
