package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErrandRiderOfGondor extends CardImpl {

    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY),
            "You control a legendary creature"
    );

    public ErrandRiderOfGondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Errand-Rider of Gondor enters the battlefield, draw a card. Then if you don't control a legendary creature, put a card from your hand on the bottom of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ErrandRiderOfGondorEffect()).addHint(hint));
    }

    private ErrandRiderOfGondor(final ErrandRiderOfGondor card) {
        super(card);
    }

    @Override
    public ErrandRiderOfGondor copy() {
        return new ErrandRiderOfGondor(this);
    }
}

class ErrandRiderOfGondorEffect extends OneShotEffect {

    ErrandRiderOfGondorEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card. Then if you don't control a legendary creature, " +
                "put a card from your hand on the bottom of your library";
    }

    private ErrandRiderOfGondorEffect(final ErrandRiderOfGondorEffect effect) {
        super(effect);
    }

    @Override
    public ErrandRiderOfGondorEffect copy() {
        return new ErrandRiderOfGondorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        if (game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY, source, game, 1
        ) || player.getHand().isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCardInHand();
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card == null || player.putCardsOnBottomOfLibrary(card, game, source, false);
    }
}
