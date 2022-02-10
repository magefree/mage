package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class SarkhanDragonsoul extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Dragon creature cards");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public SarkhanDragonsoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);
        this.setStartingLoyalty(5);

        // +2: Sarkhan, Dragonsoul deals 1 damage to each opponent and each creature your opponents control.
        Ability ability = new LoyaltyAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), 2);
        ability.addEffect(new DamageAllEffect(1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
                .setText("and each creature your opponents control")
        );

        this.addAbility(ability);


        // −3: Sarkhan, Dragonsoul deals 4 damage to target player or planeswalker.
        Ability ability2 = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability2.addTarget(new TargetPlayerOrPlaneswalker());

        this.addAbility(ability2);

        // −9: Search your library for any number of Dragon creature cards, put them onto the battlefield, then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter)), -9));
    }

    private SarkhanDragonsoul(final SarkhanDragonsoul card) {
        super(card);
    }

    @Override
    public SarkhanDragonsoul copy() {
        return new SarkhanDragonsoul(this);
    }
}
