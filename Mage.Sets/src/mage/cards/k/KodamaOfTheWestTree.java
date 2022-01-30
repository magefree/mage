package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KodamaOfTheWestTree extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public KodamaOfTheWestTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Modified creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("modified creatures you control have trample")));

        // Whenever a modified creature you control deals combat damage to an opponent, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
                ), filter, false, SetTargetPointer.NONE, true,
                false, TargetController.OPPONENT
        ));
    }

    private KodamaOfTheWestTree(final KodamaOfTheWestTree card) {
        super(card);
    }

    @Override
    public KodamaOfTheWestTree copy() {
        return new KodamaOfTheWestTree(this);
    }
}
