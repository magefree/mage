package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author padfoothelix
 */
public final class TheFiveDoctors extends CardImpl {

    private static final FilterCard filter = new FilterCard("Doctor cards"); 

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    public TheFiveDoctors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");
        

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Search your library and/or graveyard for up to five Doctor cards, reveal them, and put them into your hand. If you search your library this way, shuffle. If this spell was kicked, put those cards onto the battlefield instead of putting them into your hand.
        this.getSpellAbility().addEffect(
                new TheFiveDoctorsSearchLibraryGraveyardEffect(5, filter)
        );
    }

    private TheFiveDoctors(final TheFiveDoctors card) {
        super(card);
    }

    @Override
    public TheFiveDoctors copy() {
        return new TheFiveDoctors(this);
    }
}

class TheFiveDoctorsSearchLibraryGraveyardEffect extends OneShotEffect { 

    private int cardsToSearch;
    private FilterCard filter;

    public TheFiveDoctorsSearchLibraryGraveyardEffect(int cardsToSearch, FilterCard filter) {
        super(Outcome.Benefit);
        this.cardsToSearch = cardsToSearch;
        this.filter = filter;
        staticText = "search your library and/or graveyard for up to " + CardUtil.numberToText(cardsToSearch) + " " + filter.getMessage() + 
                ", reveal them, and put them into your hand. If you search your library this way, shuffle. " +
                "If this spell was kicked, put those cards onto the battlefield instead of putting them into you hand.";
    }

    protected TheFiveDoctorsSearchLibraryGraveyardEffect(final TheFiveDoctorsSearchLibraryGraveyardEffect effect) {
        super(effect);
        this.cardsToSearch = effect.cardsToSearch;
        this.filter = effect.filter;

    }

    @Override
    public TheFiveDoctorsSearchLibraryGraveyardEffect copy() {
        return new TheFiveDoctorsSearchLibraryGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Cards cardsFound = new CardsImpl();
        boolean needShuffle = false;
        int cardsLeftToSearch = cardsToSearch;
        if (controller == null || sourceObject == null) {
            return false;
        }
        if (controller.chooseUse(outcome, "Search your library for up to " + CardUtil.numberToText(cardsToSearch) + " " + filter.getMessage() + '?', source, game)) {
            TargetCardInLibrary targetLib = new TargetCardInLibrary(0, cardsToSearch, filter);
            targetLib.clearChosen();
            if (controller.searchLibrary(targetLib, source, game)) {
                if (!targetLib.getTargets().isEmpty()) {
                    for (UUID cardId : targetLib.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            cardsFound.add(card);
                        }
                    }
                }
            }
            needShuffle = true;
        }
        cardsLeftToSearch = cardsToSearch - cardsFound.count(filter, game);
        if (cardsLeftToSearch > 0 && controller.chooseUse(outcome, "Search your graveyard for up to " + CardUtil.numberToText(cardsLeftToSearch) + " " + filter.getMessage() + '?', source, game)) {
            TargetCard targetGrave = new TargetCardInYourGraveyard(0, cardsLeftToSearch, filter, true);
            targetGrave.clearChosen();
            if (controller.chooseTarget(outcome, controller.getGraveyard(), targetGrave, source, game)) {
                if (!targetGrave.getTargets().isEmpty()) {
                    for (UUID cardId : targetGrave.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            cardsFound.add(card);
                        }
                    }
                }
            }
        }
        if (cardsFound != null) {
            controller.revealCards(sourceObject.getIdName(), cardsFound, game);
            if (KickedCondition.ONCE.apply(game, source)) {
                controller.moveCards(cardsFound, Zone.BATTLEFIELD, source, game);
            } else {
                controller.moveCards(cardsFound, Zone.HAND, source, game);
            }
        }
        if (needShuffle) {
            controller.shuffleLibrary(source, game);
        }
        return true;
    }
}

