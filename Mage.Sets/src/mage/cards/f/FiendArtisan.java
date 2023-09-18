package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiendArtisan extends CardImpl {

    private static final DynamicValue xValue
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public FiendArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fiend Artisan gets +1/+1 for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, xValue, Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+1 for each creature card in your graveyard")));

        // {X}{B/G}, {T}, Sacrifice another creature: Search your library for a creature card with converted mana cost X or less, put it onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new FiendArtisanEffect(), new ManaCostsImpl<>("{X}{B/G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private FiendArtisan(final FiendArtisan card) {
        super(card);
    }

    @Override
    public FiendArtisan copy() {
        return new FiendArtisan(this);
    }
}

class FiendArtisanEffect extends OneShotEffect {

    FiendArtisanEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a creature card with mana value X or less, " +
                "put it onto the battlefield, then shuffle";
    }

    private FiendArtisanEffect(final FiendArtisanEffect effect) {
        super(effect);
    }

    @Override
    public FiendArtisanEffect copy() {
        return new FiendArtisanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)).apply(game, source);
    }
}