package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernBrawler extends CardImpl {

    public TavernBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "At the beginning of your upkeep, exile the top card of your library. This creature gets +X/+0 until end of turn, where X is that card's mana value. You may play that card this turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new BeginningOfUpkeepTriggeredAbility(
                        new TavernBrawlerEffect(), TargetController.YOU, false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private TavernBrawler(final TavernBrawler card) {
        super(card);
    }

    @Override
    public TavernBrawler copy() {
        return new TavernBrawler(this);
    }
}

class TavernBrawlerEffect extends OneShotEffect {

    TavernBrawlerEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. This creature gets +X/+0 until end of turn, " +
                "where X is that card's mana value. You may play that card this turn";
    }

    private TavernBrawlerEffect(final TavernBrawlerEffect effect) {
        super(effect);
    }

    @Override
    public TavernBrawlerEffect copy() {
        return new TavernBrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        game.addEffect(new BoostSourceEffect(card.getManaValue(), 0, Duration.EndOfTurn), source);
        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        return true;
    }
}
