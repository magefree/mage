package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class EnduringIdeal extends CardImpl {

    public EnduringIdeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Search your library for an enchantment card and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new EnduringIdealEffect());

        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());

    }

    private EnduringIdeal(final EnduringIdeal card) {
        super(card);
    }

    @Override
    public EnduringIdeal copy() {
        return new EnduringIdeal(this);
    }
}

class EnduringIdealEffect extends OneShotEffect {

    public EnduringIdealEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for an enchantment card, put it onto the battlefield, then shuffle";
    }

    public EnduringIdealEffect(final EnduringIdealEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_ENCHANTMENT);
            controller.searchLibrary(target, source, game);
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard == null) {
                applied = false;
            } else {
                applied = controller.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
                controller.shuffleLibrary(source, game);
            }
        }
        return applied;
    }

    @Override
    public EnduringIdealEffect copy() {
        return new EnduringIdealEffect(this);
    }
}
