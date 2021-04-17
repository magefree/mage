package mage.cards.s;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlashTheRanks extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creatures and planeswalkers except for commanders");

    static {
        filter.add(Predicates.not(CommanderPredicate.instance));
    }

    public SlashTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all creatures and planeswalkers except for commanders.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private SlashTheRanks(final SlashTheRanks card) {
        super(card);
    }

    @Override
    public SlashTheRanks copy() {
        return new SlashTheRanks(this);
    }
}
