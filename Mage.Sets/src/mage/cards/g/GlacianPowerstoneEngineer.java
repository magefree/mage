package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlacianPowerstoneEngineer extends CardImpl {

    public GlacianPowerstoneEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // {T}, Tap X untapped artifacts you control: Look at the top X cards of your library. Put one of those cards into your hand and the rest into your graveyard.
        Ability ability = new SimpleActivatedAbility(new GlacianPowerstoneEngineerEffect(), new TapSourceCost());
        ability.addCost(new GlacianPowerstoneEngineerCost());
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private GlacianPowerstoneEngineer(final GlacianPowerstoneEngineer card) {
        super(card);
    }

    @Override
    public GlacianPowerstoneEngineer copy() {
        return new GlacianPowerstoneEngineer(this);
    }
}

class GlacianPowerstoneEngineerEffect extends OneShotEffect {

    GlacianPowerstoneEngineerEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library. " +
                "Put one of those cards into your hand and the rest into your graveyard";
    }

    private GlacianPowerstoneEngineerEffect(final GlacianPowerstoneEngineerEffect effect) {
        super(effect);
    }

    @Override
    public GlacianPowerstoneEngineerEffect copy() {
        return new GlacianPowerstoneEngineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        for (Cost cost : source.getCosts()) {
            if (cost instanceof GlacianPowerstoneEngineerCost) {
                xValue = ((GlacianPowerstoneEngineerCost) cost).getAmount();
                break;
            }
        }
        if (xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard targetCard = new TargetCardInLibrary(1, StaticFilters.FILTER_CARD);
        player.choose(outcome, cards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card != null && player.moveCards(card, Zone.HAND, source, game)) {
            cards.remove(card);
        }
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}

class GlacianPowerstoneEngineerCost extends VariableCostImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    GlacianPowerstoneEngineerCost() {
        super(VariableCostType.NORMAL, "controlled untapped artifacts you would like to tap");
        this.text = "Tap X untapped artifacts you control";
    }

    private GlacianPowerstoneEngineerCost(final GlacianPowerstoneEngineerCost cost) {
        super(cost);
    }

    @Override
    public GlacianPowerstoneEngineerCost copy() {
        return new GlacianPowerstoneEngineerCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new TapTargetCost(new TargetControlledPermanent(xValue, xValue, filter, true));
    }
}
