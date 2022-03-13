package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NightmareIncursion extends CardImpl {

    public NightmareIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");

        // Search target player's library for up to X cards, where X is the number of Swamps you control, and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new NightmareIncursionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private NightmareIncursion(final NightmareIncursion card) {
        super(card);
    }

    @Override
    public NightmareIncursion copy() {
        return new NightmareIncursion(this);
    }
}

class NightmareIncursionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

    public NightmareIncursionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target player's library for up to X cards, " +
                "where X is the number of Swamps you control, and exile them. " +
                "Then that player shuffles";
    }

    public NightmareIncursionEffect(final NightmareIncursionEffect effect) {
        super(effect);
    }

    @Override
    public NightmareIncursionEffect copy() {
        return new NightmareIncursionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller == null || targetPlayer == null) {
            return false;
        }
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_CARD);
        if (controller.searchLibrary(target, source, game, targetPlayer.getId())) {
            Cards cards = new CardsImpl(target.getTargets());
            controller.moveCards(cards, Zone.EXILED, source, game);
        }
        targetPlayer.shuffleLibrary(source, game);
        return true;
    }
}
