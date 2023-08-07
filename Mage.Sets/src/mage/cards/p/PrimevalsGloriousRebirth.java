package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class PrimevalsGloriousRebirth extends CardImpl {

    public PrimevalsGloriousRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Return all legendary permanent cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new PrimevalsGloriousRebirthEffect());
    }

    private PrimevalsGloriousRebirth(final PrimevalsGloriousRebirth card) {
        super(card);
    }

    @Override
    public PrimevalsGloriousRebirth copy() {
        return new PrimevalsGloriousRebirth(this);
    }

}

class PrimevalsGloriousRebirthEffect extends OneShotEffect {

    private static final FilterPermanentCard filter = new FilterPermanentCard();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public PrimevalsGloriousRebirthEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return all legendary permanent cards from your graveyard to the battlefield";
    }

    public PrimevalsGloriousRebirthEffect(final PrimevalsGloriousRebirthEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }

    @Override
    public PrimevalsGloriousRebirthEffect copy() {
        return new PrimevalsGloriousRebirthEffect(this);
    }
}
