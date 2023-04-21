package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetousUrge extends CardImpl {

    public CovetousUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U/B}{U/B}{U/B}{U/B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addEffect(new CovetousUrgeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CovetousUrge(final CovetousUrge card) {
        super(card);
    }

    @Override
    public CovetousUrge copy() {
        return new CovetousUrge(this);
    }
}

class CovetousUrgeEffect extends OneShotEffect {

    CovetousUrgeEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. You choose a nonland card from that player's graveyard " +
                "or hand and exile it. You may cast that card for as long as it remains exiled," +
                " and you may spend mana as though it were mana of any color to cast that spell.";
    }

    private CovetousUrgeEffect(final CovetousUrgeEffect effect) {
        super(effect);
    }

    @Override
    public CovetousUrgeEffect copy() {
        return new CovetousUrgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);

        TargetCard target; // TODO: fix skip exile on wrong place (see Nicol Bolas, Dragon-God)
        if (player.getGraveyard().isEmpty() || controller.chooseUse(Outcome.Benefit, // AI must use hand first
                "Exile a nonland card from " + player.getName() + "'s hand or graveyard",
                "", "Hand", "Graveyard", source, game)) {
            if (player.getHand().isEmpty()) {
                return true;
            }
            target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
            controller.choose(Outcome.Exile, player.getHand(), target, source, game);
        } else {
            target = new TargetCard(Zone.GRAVEYARD, StaticFilters.FILTER_CARD_A_NON_LAND);
            controller.choose(Outcome.Exile, player.getGraveyard(), target, source, game);
        }

        // use same player's zone for all Covetous Urge instances
        UUID exileZone = CardUtil.getExileZoneId(controller.getId() + " - Covetous Urge", game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !controller.moveCardsToExile(card, source, game, true, exileZone, "Covetous Urge - can cast with any mana")) {
            return false;
        }
        if (card.getSpellAbility() == null) {
            return true;
        }
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        return true;
    }
}
