
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Cultivate extends CardImpl {

    public Cultivate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new CultivateEffect());

    }

    public Cultivate(final Cultivate card) {
        super(card);
    }

    @Override
    public Cultivate copy() {
        return new Cultivate(this);
    }

}

class CultivateEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public CultivateEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Then shuffle your library";
    }

    public CultivateEffect(final CultivateEffect effect) {
        super(effect);
    }

    @Override
    public CultivateEffect copy() {
        return new CultivateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards revealed = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (target.getTargets().size() == 2) {
                    TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                    controller.choose(Outcome.Benefit, revealed, target2, game);
                    Card card = revealed.get(target2.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        revealed.remove(card);
                    }
                    card = revealed.getCards(game).iterator().next();
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                } else if (target.getTargets().size() == 1) {
                    Card card = revealed.getCards(game).iterator().next();
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    }
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;

    }

}
