package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class BazaarOfWonders extends CardImpl {

    public BazaarOfWonders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.addSuperType(SuperType.WORLD);

        // When Bazaar of Wonders enters the battlefield, exile all cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllPlayersEffect()));

        // Whenever a player casts a spell, counter it if a card with the same name is in 
        // a graveyard or a nontoken permanent with the same name is on the battlefield.
        this.addAbility(new SpellCastAllTriggeredAbility(new BazaarOfWondersEffect(),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private BazaarOfWonders(final BazaarOfWonders card) {
        super(card);
    }

    @Override
    public BazaarOfWonders copy() {
        return new BazaarOfWonders(this);
    }
}

class BazaarOfWondersEffect extends OneShotEffect {

    public BazaarOfWondersEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter it if a card with the same name is in a graveyard "
                + "or a nontoken permanent with the same name is on the battlefield";
    }

    public BazaarOfWondersEffect(final BazaarOfWondersEffect effect) {
        super(effect);
    }

    @Override
    public BazaarOfWondersEffect copy() {
        return new BazaarOfWondersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        String spellName = spell.getName();
        FilterPermanent filter1 = new FilterPermanent();
        filter1.add(new NamePredicate(spellName));
        filter1.add(TokenPredicate.FALSE);
        if (!game.getBattlefield().getActivePermanents(filter1, 
                source.getControllerId(), game).isEmpty()) {
            game.getStack().counter(spell.getId(), source, game);
            return true;
        }
        FilterCard filter2 = new FilterCard();
        filter2.add(new NamePredicate(spellName));
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (player.getGraveyard().count(filter2, game) > 0) {
                return game.getStack().counter(spell.getId(), source, game);
            }
        }
        return false;
    }
}
