package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YaroksFenlurker extends CardImpl {

    public YaroksFenlurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Yarok's Fenlurker enters the battlefield, each opponent exiles a card from their hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new YaroksFenlurkerEffect()));

        // {2}{B}: Yarok's Fenlurker gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}")
        ));
    }

    private YaroksFenlurker(final YaroksFenlurker card) {
        super(card);
    }

    @Override
    public YaroksFenlurker copy() {
        return new YaroksFenlurker(this);
    }
}

class YaroksFenlurkerEffect extends OneShotEffect {

    YaroksFenlurkerEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles a card from their hand";
    }

    private YaroksFenlurkerEffect(final YaroksFenlurkerEffect effect) {
        super(effect);
    }

    @Override
    public YaroksFenlurkerEffect copy() {
        return new YaroksFenlurkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean applied = false;
        Map<UUID, Cards> cardsToExile = new HashMap<>();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null
                    || opponent.getHand().isEmpty()) {
                continue;
            }
            int numberOfCardsToExile = Math.min(1, opponent.getHand().size());
            Target target = new TargetCardInHand(numberOfCardsToExile, StaticFilters.FILTER_CARD);
            target.setRequired(true);
            if (opponent.chooseTarget(Outcome.Exile, target, source, game)) {
                Cards cards = new CardsImpl(target.getTargets());
                cardsToExile.put(opponentId, cards);
            }
        }
        Cards cardsOpponentsChoseToExile = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !cardsToExile.containsKey(opponentId)) {
                continue;
            }
            cardsOpponentsChoseToExile.addAll(cardsToExile.get(opponentId));
            opponent.moveCards(cardsOpponentsChoseToExile, Zone.EXILED, source, game);
            applied = true;
        }
        return applied;
    }
}