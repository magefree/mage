package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NorthamptonFarm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public NorthamptonFarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Exile target creature you own.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this land: Return a creature card exiled with this land to the battlefield under your control. Return each other card exiled with this land to its owner's hand.
        ability = new SimpleActivatedAbility(new NorthamptonFarmEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NorthamptonFarm(final NorthamptonFarm card) {
        super(card);
    }

    @Override
    public NorthamptonFarm copy() {
        return new NorthamptonFarm(this);
    }
}

class NorthamptonFarmEffect extends OneShotEffect {

    NorthamptonFarmEffect() {
        super(Outcome.Benefit);
        staticText = "return a creature card exiled with this land to the battlefield under your control. " +
                "Return each other card exiled with this land to its owner's hand";
    }

    private NorthamptonFarmEffect(final NorthamptonFarmEffect effect) {
        super(effect);
    }

    @Override
    public NorthamptonFarmEffect copy() {
        return new NorthamptonFarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone);
        Cards creatureCards = new CardsImpl(cards.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        Card card;
        switch (creatureCards.size()) {
            case 0:
                card = null;
                break;
            case 1:
                card = creatureCards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE);
                player.choose(outcome, creatureCards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            cards.remove(card);
        }
        player.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
