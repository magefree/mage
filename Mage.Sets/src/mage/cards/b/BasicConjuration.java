package mage.cards.b;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class BasicConjuration extends CardImpl {

    public BasicConjuration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");
        
        this.subtype.add(SubType.LESSON);

        // Look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. You gain 3 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                6, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private BasicConjuration(final BasicConjuration card) {
        super(card);
    }

    @Override
    public BasicConjuration copy() {
        return new BasicConjuration(this);
    }
}
