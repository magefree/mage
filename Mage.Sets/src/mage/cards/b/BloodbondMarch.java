package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
 */
public final class BloodbondMarch extends CardImpl {

    public BloodbondMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");
        // Whenever a player casts a creature spell, each player returns all cards with the same name as that spell from their graveyard to the battlefield.
        this.addAbility(new SpellCastAllTriggeredAbility(new BloodbondMarchEffect(), new FilterCreatureSpell(), false, SetTargetPointer.SPELL));
    }

    private BloodbondMarch(final BloodbondMarch card) {
        super(card);
    }

    @Override
    public BloodbondMarch copy() {
        return new BloodbondMarch(this);
    }

    private class BloodbondMarchEffect extends OneShotEffect {

        BloodbondMarchEffect() {
            super(Outcome.Benefit);
            staticText = "each player returns all cards with the same name as that spell from their graveyard to the battlefield";
        }

        private BloodbondMarchEffect(final BloodbondMarchEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());

            if (controller == null || game.getPermanentOrLKIBattlefield(source.getSourceId()) == null) {
                return false;
            }

            Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));

            if (spell == null) {
                return false;
            }

            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(spell.getName()));

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
                }
            }

            return true;
        }

        @Override
        public BloodbondMarchEffect copy() {
            return new BloodbondMarchEffect(this);
        }
    }
}
