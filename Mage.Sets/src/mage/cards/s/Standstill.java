package mage.cards.s;

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
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Standstill extends CardImpl {

    public Standstill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When a player casts a spell, sacrifice Standstill. If you do, each of that player's opponents draws three cards.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new StandstillEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ).setTriggerPhrase("When a player casts a spell, "));
    }

    private Standstill(final Standstill card) {
        super(card);
    }

    @Override
    public Standstill copy() {
        return new Standstill(this);
    }
}

class StandstillEffect extends OneShotEffect {

    StandstillEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}. If you do, each of that player's opponents draws three cards";
    }

    private StandstillEffect(final StandstillEffect effect) {
        super(effect);
    }

    @Override
    public StandstillEffect copy() {
        return new StandstillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.sacrifice(source, game)) {
            for (UUID uuid : game.getOpponents(this.getTargetPointer().getFirst(game, source))) {
                Player player = game.getPlayer(uuid);
                if (player != null) {
                    player.drawCards(3, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
