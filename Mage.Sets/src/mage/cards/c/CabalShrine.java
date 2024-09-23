package mage.cards.c;

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
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabalShrine extends CardImpl {

    public CabalShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // Whenever a player casts a spell, that player discards X cards, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new CabalShrineEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));
    }

    private CabalShrine(final CabalShrine card) {
        super(card);
    }

    @Override
    public CabalShrine copy() {
        return new CabalShrine(this);
    }
}

class CabalShrineEffect extends OneShotEffect {

    CabalShrineEffect() {
        super(Outcome.Benefit);
        staticText = "that player discards X cards, where X is the number " +
                "of cards in all graveyards with the same name as that spell";
    }

    private CabalShrineEffect(final CabalShrineEffect effect) {
        super(effect);
    }

    @Override
    public CabalShrineEffect copy() {
        return new CabalShrineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        int amount = game
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
        return amount > 0 && !player.discard(amount, false, false, source, game).isEmpty();
    }
}
