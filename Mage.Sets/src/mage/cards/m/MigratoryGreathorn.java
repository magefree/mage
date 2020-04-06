package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MigratoryGreathorn extends CardImpl {

    public MigratoryGreathorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Mutate {2}{G}
        this.addAbility(new MutateAbility(this, "{2}{G}"));

        // Whenever this creature mutates, search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new MutatesSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        )));
    }

    private MigratoryGreathorn(final MigratoryGreathorn card) {
        super(card);
    }

    @Override
    public MigratoryGreathorn copy() {
        return new MigratoryGreathorn(this);
    }
}
