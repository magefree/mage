package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ManaBreach extends CardImpl {

    public ManaBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever a player casts a spell, that player returns a land they control to its owner's hand.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new ManaBreachEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));
    }

    private ManaBreach(final ManaBreach card) {
        super(card);
    }

    @Override
    public ManaBreach copy() {
        return new ManaBreach(this);
    }
}

class ManaBreachEffect extends OneShotEffect {

    ManaBreachEffect() {
        super(Outcome.Benefit);
        staticText = "that player returns a land they control to its owner's hand";
    }

    private ManaBreachEffect(final ManaBreachEffect effect) {
        super(effect);
    }

    @Override
    public ManaBreachEffect copy() {
        return new ManaBreachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                player.getId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        return player.moveCards(game.getPermanent(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
