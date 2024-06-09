package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CruelGrimnarch extends CardImpl {

    public CruelGrimnarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.PHYREXIAN, SubType.CLERIC);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Cruel Grimnarch enters the battlefield, each opponent discards a card. For each opponent who can't, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CruelGrimnarchEffect()));
    }

    private CruelGrimnarch(final CruelGrimnarch card) {
        super(card);
    }

    @Override
    public CruelGrimnarch copy() {
        return new CruelGrimnarch(this);
    }
}

class CruelGrimnarchEffect extends OneShotEffect {

    CruelGrimnarchEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent discards a card. For each opponent who can't, you gain 4 life";
    }

    private CruelGrimnarchEffect(final CruelGrimnarchEffect effect) {
        super(effect);
    }

    @Override
    public CruelGrimnarchEffect copy() {
        return new CruelGrimnarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();

        // choose cards to discard
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int numberOfCardsToDiscard = Math.min(1, player.getHand().size());
            Cards cards = new CardsImpl();
            Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, StaticFilters.FILTER_CARD, playerId);
            player.chooseTarget(outcome, target, source, game);
            cards.addAll(target.getTargets());
            cardsToDiscard.put(playerId, cards);
        }

        // discard all chosen cards
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amountDiscarded = player.discard(cardsToDiscard.get(playerId), false, source, game).size();
            if (amountDiscarded == 0 && controller != null) {
                controller.gainLife(4, game, source);
            }
        }
        return true;
    }
}
