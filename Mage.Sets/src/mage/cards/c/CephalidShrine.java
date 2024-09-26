package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
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
public final class CephalidShrine extends CardImpl {

    public CephalidShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever a player casts a spell, counter that spell unless that player pays {X}, where X is the number of cards in all graveyards with the same name as the spell.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new CephalidShrineEffect(), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.PLAYER
        ));
    }

    private CephalidShrine(final CephalidShrine card) {
        super(card);
    }

    @Override
    public CephalidShrine copy() {
        return new CephalidShrine(this);
    }
}

class CephalidShrineEffect extends OneShotEffect {

    CephalidShrineEffect() {
        super(Outcome.Benefit);
        staticText = "counter that spell unless that player pays {X}, " +
                "where X is the number of cards in all graveyards with the same name as the spell";
    }

    private CephalidShrineEffect(final CephalidShrineEffect effect) {
        super(effect);
    }

    @Override
    public CephalidShrineEffect copy() {
        return new CephalidShrineEffect(this);
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
                .filter(card -> card.sharesName(spell, game))
                .mapToInt(x -> 1)
                .sum();
        Cost cost = new GenericManaCost(amount);
        return (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(outcome, "Pay {" + amount + "}?", source, game)
                && cost.pay(source, game, source, player.getId(), false))
                || game.getStack().counter(spell.getId(), source, game);
    }
}
