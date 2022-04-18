package mage.cards.n;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class NaggingThoughts extends CardImpl {

    public NaggingThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD));

        // Madness {1}{U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{1}{U}")));
    }

    private NaggingThoughts(final NaggingThoughts card) {
        super(card);
    }

    @Override
    public NaggingThoughts copy() {
        return new NaggingThoughts(this);
    }
}
