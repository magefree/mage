
package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RuinInTheirWake extends CardImpl {

    public RuinInTheirWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Search your library for a basic land card and reveal it. You may put that card onto the battlefield tapped if you control a land named Wastes. Otherwise, put that card into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RuinInTheirWakeEffect());
    }

    private RuinInTheirWake(final RuinInTheirWake card) {
        super(card);
    }

    @Override
    public RuinInTheirWake copy() {
        return new RuinInTheirWake(this);
    }
}

class RuinInTheirWakeEffect extends OneShotEffect {

    private static final FilterLandPermanent filterWastes = new FilterLandPermanent();

    static {
        filterWastes.add(new NamePredicate("Wastes"));
    }

    public RuinInTheirWakeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a basic land card and reveal it. You may put that card onto the battlefield tapped if you control a land named Wastes. Otherwise, put that card into your hand. Then shuffle";
    }

    private RuinInTheirWakeEffect(final RuinInTheirWakeEffect effect) {
        super(effect);
    }

    @Override
    public RuinInTheirWakeEffect copy() {
        return new RuinInTheirWakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
            if (controller.searchLibrary(target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards cardsToReveal = new CardsImpl(card);
                    controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
                    boolean controlWastes = game.getBattlefield().countAll(filterWastes, controller.getId(), game) > 0;
                    if (controlWastes && controller.chooseUse(outcome, "Put " + card.getLogName() + " onto battlefield tapped?", source, game)) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
                    } else {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
