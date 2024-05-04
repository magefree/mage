package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HeartlessConscription extends CardImpl {

    public HeartlessConscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");

        // Exile all creatures. For each card exiled this way, you may play that card for as long as it remains exiled, and mana of any type can be spent to cast that spell. Exile Heartless Conscription.
        this.getSpellAbility().addEffect(new HeartlessConscriptionEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private HeartlessConscription(final HeartlessConscription card) {
        super(card);
    }

    @Override
    public HeartlessConscription copy() {
        return new HeartlessConscription(this);
    }
}

class HeartlessConscriptionEffect extends OneShotEffect {

    HeartlessConscriptionEffect() {
        super(Outcome.Exile);
        staticText = "Exile all creatures. "
                + "For each card exiled this way, you may play that card for as long as it remains exiled, "
                + "and mana of any type can be spent to cast that spell.";
    }

    private HeartlessConscriptionEffect(final HeartlessConscriptionEffect effect) {
        super(effect);
    }

    @Override
    public HeartlessConscriptionEffect copy() {
        return new HeartlessConscriptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)
                .stream()
                .forEach(cards::add);
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
        game.getState().processAction(game);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfGame, true);
        }
        return true;
    }

}