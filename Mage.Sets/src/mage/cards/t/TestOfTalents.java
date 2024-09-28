package mage.cards.t;

import mage.abilities.effects.common.CounterTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TestOfTalents extends CardImpl {

    public TestOfTalents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target instant or sorcery spell. Search its controller's graveyard, hand, and library for any number of cards with the same name as that spell and exile them. That player shuffles, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(new CounterTargetAndSearchGraveyardHandLibraryEffect(true, "its controller's", "any number of cards with the same name as that spell", true));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private TestOfTalents(final TestOfTalents card) {
        super(card);
    }

    @Override
    public TestOfTalents copy() {
        return new TestOfTalents(this);
    }
}
