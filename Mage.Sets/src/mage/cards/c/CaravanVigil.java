
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class CaravanVigil extends CardImpl {

    public CaravanVigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        // <i>Morbid</i> &mdash; You may put that card onto the battlefield instead of putting it into your hand if a creature died this turn.
        this.getSpellAbility().addEffect(new CaravanVigilEffect());
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private CaravanVigil(final CaravanVigil card) {
        super(card);
    }

    @Override
    public CaravanVigil copy() {
        return new CaravanVigil(this);
    }
}

class CaravanVigilEffect extends OneShotEffect {

    public CaravanVigilEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Search your library for a basic land card, reveal it, put it into your hand, then shuffle.<br>"
                + "<i>Morbid</i> &mdash; You may put that card onto the battlefield instead of putting it into your hand if a creature died this turn";
    }

    private CaravanVigilEffect(final CaravanVigilEffect effect) {
        super(effect);
    }

    @Override
    public CaravanVigilEffect copy() {
        return new CaravanVigilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
            if (controller.searchLibrary(target, source, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    if (MorbidCondition.instance.apply(game, source)
                            && controller.chooseUse(Outcome.PutLandInPlay, "Put the card onto the battlefield instead?", source, game)) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    } else {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                    controller.revealCards(sourceObject.getIdName(), cards, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
