package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

public final class RazakethsRite extends CardImpl {

    public RazakethsRite(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Search your library for a card and put that card into your hand
        // Then shuffle your library
        TargetCardInLibrary target = new TargetCardInLibrary();
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target));

        // Cycling {B}

        addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));
    }

    public RazakethsRite(final RazakethsRite razakethsRite){
        super(razakethsRite);
    }

    public RazakethsRite copy(){
        return new RazakethsRite(this);
    }
}
