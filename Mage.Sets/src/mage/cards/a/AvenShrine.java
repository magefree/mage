package mage.cards.a;

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
public final class AvenShrine extends CardImpl {

    public AvenShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Whenever a player casts a spell, that player gains X life, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new AvenShrineEffect(), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.PLAYER
        ));
    }

    private AvenShrine(final AvenShrine card) {
        super(card);
    }

    @Override
    public AvenShrine copy() {
        return new AvenShrine(this);
    }
}

class AvenShrineEffect extends OneShotEffect {

    AvenShrineEffect() {
        super(Outcome.Benefit);
        staticText = "that player gains X life, where X is the number " +
                "of cards in all graveyards with the same name as that spell";
    }

    private AvenShrineEffect(final AvenShrineEffect effect) {
        super(effect);
    }

    @Override
    public AvenShrineEffect copy() {
        return new AvenShrineEffect(this);
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
        return player.gainLife(count, game, source) > 0;
    }
}
