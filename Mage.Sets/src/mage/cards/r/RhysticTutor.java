
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public final class RhysticTutor extends CardImpl {

    public RhysticTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Unless any player pays {2}, search your library for a card, put that card into your hand, then shuffle your library.
        Effect effect = new DoUnlessAnyPlayerPaysEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false), new ManaCostsImpl<>("{2}"));
        effect.setText("Unless any player pays {2}, search your library for a card, put that card into your hand, then shuffle");
        this.getSpellAbility().addEffect(effect);
    }

    private RhysticTutor(final RhysticTutor card) {
        super(card);
    }

    @Override
    public RhysticTutor copy() {
        return new RhysticTutor(this);
    }
}
