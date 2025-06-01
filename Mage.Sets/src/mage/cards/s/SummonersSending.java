package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonersSending extends CardImpl {

    public SummonersSending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your end step, you may exile target creature card from a graveyard. If you do, create a 1/1 white Spirit creature token with flying. Put a +1/+1 counter on it if the exiled card's mana value is 4 or greater.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new SummonersSendingEffect(), true);
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private SummonersSending(final SummonersSending card) {
        super(card);
    }

    @Override
    public SummonersSending copy() {
        return new SummonersSending(this);
    }
}

class SummonersSendingEffect extends OneShotEffect {

    SummonersSendingEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from a graveyard. If you do, create a " +
                "1/1 white Spirit creature token with flying. Put a +1/+1 counter on it " +
                "if the exiled card's mana value is 4 or greater";
    }

    private SummonersSendingEffect(final SummonersSendingEffect effect) {
        super(effect);
    }

    @Override
    public SummonersSendingEffect copy() {
        return new SummonersSendingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Token token = new SpiritWhiteToken();
        token.putOntoBattlefield(1, game, source);
        if (card.getManaValue() < 4) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Optional.ofNullable(tokenId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}
