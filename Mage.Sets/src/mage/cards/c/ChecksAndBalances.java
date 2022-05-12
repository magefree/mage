package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ChecksAndBalances extends CardImpl {

    public ChecksAndBalances(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Cast this spell only if there are three or more players in the game.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(ChecksAndBalancesCondition.instance, "Cast this spell only if there are three or more players in the game"));

        // Whenever a player casts a spell, each of that player’s opponents may discard a card. If they do, counter that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new ChecksAndBalancesEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private ChecksAndBalances(final ChecksAndBalances card) {
        super(card);
    }

    @Override
    public ChecksAndBalances copy() {
        return new ChecksAndBalances(this);
    }
}

enum ChecksAndBalancesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayerList().size() >= 3;
    }

    @Override
    public String toString() {
        return "there are three or more players in the game";
    }
}

class ChecksAndBalancesEffect extends OneShotEffect {

    public ChecksAndBalancesEffect() {
        super(Outcome.Detriment);
        staticText = "each of that player's opponents may discard a card. If they do, counter that spell";
    }

    public ChecksAndBalancesEffect(final ChecksAndBalancesEffect effect) {
        super(effect);
    }

    @Override
    public ChecksAndBalancesEffect copy() {
        return new ChecksAndBalancesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            for (UUID uuid : game.getOpponents(spell.getControllerId())) {
                Player player = game.getPlayer(uuid);
                if (player != null) {
                    if (player.getHand().isEmpty()) {
                        game.informPlayers(player.getLogName() + " doesn't have a card in hand to discard to counter " + spell.getLogName() + ", effect aborted.");
                        return true;
                    }
                }
            }
            for (UUID uuid : game.getOpponents(spell.getControllerId())) {
                Player player = game.getPlayer(uuid);
                if (player != null) {
                    if (!player.chooseUse(outcome, "Discard a card to counter " + spell.getLogName() + '?', source, game)) {
                        game.informPlayers(player.getLogName() + " refuses to discard a card to counter " + spell.getLogName());
                        return true;
                    } else {
                        game.informPlayers(player.getLogName() + " agrees to discard a card to counter " + spell.getLogName());
                    }
                }
            }
            for (UUID uuid : game.getOpponents(spell.getControllerId())) {
                Player player = game.getPlayer(uuid);
                if (player != null && !player.getHand().isEmpty()) {
                    TargetCardInHand target = new TargetCardInHand();
                    if (player.choose(Outcome.Discard, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        player.discard(card, false, source, game);
                    }

                }
            }
            game.getStack().counter(spell.getId(), source, game);
            return true;
        }
        return false;
    }
}
