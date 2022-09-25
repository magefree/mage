package mage.cards.o;

import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkosHospitality extends CardImpl {

    private static final FilterCard filter = new FilterCard("Oko, the Trickster");

    static {
        filter.add(new NamePredicate("Oko, the Trickster"));
    }

    public OkosHospitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{U}");

        // Creatures you control have base power and toughness 3/3 until end of turn. You may search your library and/or graveyard for a card named Oko, the Trickster, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessAllEffect(
                3, 3, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ));
        this.getSpellAbility().addEffect(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        );
    }

    private OkosHospitality(final OkosHospitality card) {
        super(card);
    }

    @Override
    public OkosHospitality copy() {
        return new OkosHospitality(this);
    }
}
