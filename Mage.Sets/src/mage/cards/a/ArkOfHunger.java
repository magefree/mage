package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArkOfHunger extends CardImpl {

    public ArkOfHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}{W}");

        // Whenever one or more cards leave your graveyard, this artifact deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {T}: Mill a card. You may play that card this turn.
        this.addAbility(new SimpleActivatedAbility(new ArkOfHungerEffect(), new TapSourceCost()));
    }

    private ArkOfHunger(final ArkOfHunger card) {
        super(card);
    }

    @Override
    public ArkOfHunger copy() {
        return new ArkOfHunger(this);
    }
}

class ArkOfHungerEffect extends OneShotEffect {

    ArkOfHungerEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. You may play that card this turn";
    }

    private ArkOfHungerEffect(final ArkOfHungerEffect effect) {
        super(effect);
    }

    @Override
    public ArkOfHungerEffect copy() {
        return new ArkOfHungerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(1, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        }
        return true;
    }
}
