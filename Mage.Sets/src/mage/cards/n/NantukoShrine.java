
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class NantukoShrine extends CardImpl {

    public NantukoShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a player casts a spell, that player puts X 1/1 green Squirrel creature tokens onto the battlefield, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new NantukoShrineEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.SPELL));
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

    public NantukoShrineEffect() {
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
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                int count = 0;
                String name = spell.getName();
                FilterCard filterCardName = new FilterCard();
                filterCardName.add(new NamePredicate(name));
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        count += player.getGraveyard().count(filterCardName, game);
                    }
                }
                if (count > 0) {
                    new SquirrelToken().putOntoBattlefield(count, game, source, spell.getControllerId());
                }
                return true;
            }
        }
        return false;
    }
}
