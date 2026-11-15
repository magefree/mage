package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * @author muz
 */
public final class TheFiveDoctors extends CardImpl {

    public TheFiveDoctors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Search your library and/or graveyard for up to five Doctor cards, reveal them, and put them into your hand. If you search your library this way, shuffle. If this spell was kicked, put those cards onto the battlefield instead of putting them into your hand.
        this.getSpellAbility().addEffect(new TheFiveDoctorsEffect());
    }

    private TheFiveDoctors(final TheFiveDoctors card) {
        super(card);
    }

    @Override
    public TheFiveDoctors copy() {
        return new TheFiveDoctors(this);
    }
}

class TheFiveDoctorsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(SubType.DOCTOR);

    public TheFiveDoctorsEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library and/or graveyard for up to five Doctor cards, "
        + "reveal them, and put them into your hand. If you search your library this "
        + "way, shuffle. If this spell was kicked, put those cards onto the battlefield "
        + "instead of putting them into your hand.";
    }

    private TheFiveDoctorsEffect(final TheFiveDoctorsEffect effect) {
        super(effect);
    }

    @Override
    public TheFiveDoctorsEffect copy() {
        return new TheFiveDoctorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        int cardsLeft = 5;
        Cards cardsFound = new CardsImpl();
        boolean needShuffle = false;
        if (controller.chooseUse(outcome, "Search your library for up to five Doctor cards?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 5, filter);
            if (controller.searchLibrary(target, source, game)) {
                for (UUID cardId : target.getTargets()) {
                    cardsFound.add(game.getCard(cardId));
                }
            }
            needShuffle = true;
        }

        cardsLeft -= cardsFound.size();

        if (cardsLeft > 0 && controller.chooseUse(outcome, "Search your graveyard for up to " + CardUtil.numberToText(cardsLeft) + " Doctor card" + (cardsLeft > 1 ? "s" : "") + "?", source, game)) {
            TargetCard target = new TargetCardInYourGraveyard(0, cardsLeft, filter, true);
            if (controller.choose(outcome, controller.getGraveyard(), target, source, game)) {
                for (UUID cardId : target.getTargets()) {
                    cardsFound.add(game.getCard(cardId));
                }
            }
        }

        if (!cardsFound.isEmpty()) {
            controller.revealCards(source, cardsFound, game);
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
