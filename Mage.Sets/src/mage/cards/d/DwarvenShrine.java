package mage.cards.d;

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
public final class DwarvenShrine extends CardImpl {

    public DwarvenShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // Whenever a player casts a spell, Dwarven Shrine deals X damage to that player, where X is twice the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DwarvenShrineEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));
    }

    private DwarvenShrine(final DwarvenShrine card) {
        super(card);
    }

    @Override
    public DwarvenShrine copy() {
        return new DwarvenShrine(this);
    }
}

class DwarvenShrineEffect extends OneShotEffect {

    DwarvenShrineEffect() {
        super(Outcome.Detriment);
        staticText = "{this} deals X damage to that player, where X is twice " +
                "the number of cards in all graveyards with the same name as that spell.";
    }

    private DwarvenShrineEffect(final DwarvenShrineEffect effect) {
        super(effect);
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
        return count > 0 && player.damage(2 * count, source, game) > 0;
    }

    @Override
    public DwarvenShrineEffect copy() {
        return new DwarvenShrineEffect(this);
    }
}
