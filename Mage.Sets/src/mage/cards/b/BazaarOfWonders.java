package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BazaarOfWonders extends CardImpl {

    public BazaarOfWonders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.supertype.add(SuperType.WORLD);

        // When Bazaar of Wonders enters the battlefield, exile all cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllPlayersEffect()));

        // Whenever a player casts a spell, counter it if a card with the same name is in 
        // a graveyard or a nontoken permanent with the same name is on the battlefield.
        this.addAbility(new SpellCastAllTriggeredAbility(new BazaarOfWondersEffect(), false));
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

    BazaarOfWondersEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter it if a card with the same name is in a graveyard "
                + "or a nontoken permanent with the same name is on the battlefield";
    }

    private BazaarOfWondersEffect(final BazaarOfWondersEffect effect) {
        super(effect);
    }

    @Override
    public BazaarOfWondersEffect copy() {
        return new BazaarOfWondersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        FilterPermanent filter1 = new FilterPermanent();
        filter1.add(new SharesNamePredicate(spell));
        filter1.add(TokenPredicate.FALSE);
        if (!game.getBattlefield().contains(filter1, source, game, 1)) {
            return game.getStack().counter(spell.getId(), source, game);
        }
        FilterCard filter2 = new FilterCard();
        filter2.add(new SharesNamePredicate(spell));
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getGraveyard().count(filter2, game) > 0) {
                return game.getStack().counter(spell.getId(), source, game);
            }
        }
        return false;
    }
}
