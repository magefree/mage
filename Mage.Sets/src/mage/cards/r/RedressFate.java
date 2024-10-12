package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author RobertFrosty
 */
public final class RedressFate extends CardImpl {

    public RedressFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");
        

        // Return all artifact and enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new RedressFateEffect());
        
        // Miracle {3}{W}
        this.addAbility(new MiracleAbility("{3}{W}"));

    }

    private RedressFate(final RedressFate card) {
        super(card);
    }

    @Override
    public RedressFate copy() {
        return new RedressFate(this);
    }
}

class RedressFateEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard();

    RedressFateEffect() {
        super(Outcome.Benefit);
        staticText = "return all artifact and enchantment cards from your graveyard to the battlefield";
    }

    private RedressFateEffect(final RedressFateEffect effect) {
        super(effect);
    }

    @Override
    public RedressFateEffect copy() {
        return new RedressFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
    }
}