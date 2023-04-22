package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasResearchDesk extends CardImpl {

    public MishrasResearchDesk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}, Sacrifice Mishra's Research Desk: Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(new MishrasResearchDeskEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Unearth {1}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private MishrasResearchDesk(final MishrasResearchDesk card) {
        super(card);
    }

    @Override
    public MishrasResearchDesk copy() {
        return new MishrasResearchDesk(this);
    }
}

class MishrasResearchDeskEffect extends OneShotEffect {

    MishrasResearchDeskEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. Choose one of them. " +
                "Until the end of your next turn, you may play that card";
    }

    private MishrasResearchDeskEffect(final MishrasResearchDeskEffect effect) {
        super(effect);
    }

    @Override
    public MishrasResearchDeskEffect copy() {
        return new MishrasResearchDeskEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.setNotTarget(true);
                player.choose(outcome, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, false);
        return true;
    }
}
