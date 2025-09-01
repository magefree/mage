package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EspersToMagicite extends CardImpl {

    public EspersToMagicite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile each opponent's graveyard. When you do, choose up to one target creature card exiled this way. Create a token that's a copy of that card, except it's an artifact and it loses all other card types.
        this.getSpellAbility().addEffect(new EspersToMagiciteExileEffect());
    }

    private EspersToMagicite(final EspersToMagicite card) {
        super(card);
    }

    @Override
    public EspersToMagicite copy() {
        return new EspersToMagicite(this);
    }
}

class EspersToMagiciteExileEffect extends OneShotEffect {

    EspersToMagiciteExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile each opponent's graveyard. When you do, choose up to one target creature card exiled this way. " +
                "Create a token that's a copy of that card, except it's an artifact and it loses all other card types";
    }

    private EspersToMagiciteExileEffect(final EspersToMagiciteExileEffect effect) {
        super(effect);
    }

    @Override
    public EspersToMagiciteExileEffect copy() {
        return new EspersToMagiciteExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                cards.addAll(opponent.getGraveyard());
            }
        }
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new EspersToMagiciteTokenEffect(), false);
        ability.addTarget(new TargetCardInExile(
                0, 1,
                StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, source)
        ));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class EspersToMagiciteTokenEffect extends OneShotEffect {

    EspersToMagiciteTokenEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target creature card exiled this way. " +
                "Create a token that's a copy of that card, except it's an artifact and it loses all other card types";
    }

    private EspersToMagiciteTokenEffect(final EspersToMagiciteTokenEffect effect) {
        super(effect);
    }

    @Override
    public EspersToMagiciteTokenEffect copy() {
        return new EspersToMagiciteTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return card != null &&
                new CreateTokenCopyTargetEffect()
                        .setPermanentModifier(token -> {
                            token.removeAllCardTypes();
                            token.addCardType(CardType.ARTIFACT);
                        })
                        .setTargetPointer(new FixedTarget(card, game))
                        .apply(game, source);
    }
}
