package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NantukoShrine extends CardImpl {

    public NantukoShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a player casts a spell, that player puts X 1/1 green Squirrel creature tokens onto the battlefield, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new NantukoShrineEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.PLAYER
        ));
    }

    private NantukoShrine(final NantukoShrine card) {
        super(card);
    }

    @Override
    public NantukoShrine copy() {
        return new NantukoShrine(this);
    }
}

class NantukoShrineEffect extends OneShotEffect {

    NantukoShrineEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "that player creates X 1/1 green Squirrel creature tokens, where X is the number of cards in all graveyards with the same name as that spell";
    }

    private NantukoShrineEffect(final NantukoShrineEffect effect) {
        super(effect);
    }

    @Override
    public NantukoShrineEffect copy() {
        return new NantukoShrineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        int count = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(c -> c.sharesName(spell, game))
                .mapToInt(x -> 1)
                .sum();
        return count > 0 && new SquirrelToken().putOntoBattlefield(count, game, source, player.getId());
    }
}
