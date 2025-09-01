package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheDayOfTheDoctor extends CardImpl {

    public TheDayOfTheDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Exile cards from the top of your library until you exile a legendary card. You may play that card for as long as The Day of the Doctor remains on the battlefield. Put the rest of those exiled cards on the bottom of your library in a random order.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III, new TheDayOfTheDoctorExileEffect());

        // IV -- Choose up to three Doctors. You may exile all other creatures. If you do, The Day of the Doctor deals 13 damage to you.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new TheDayOfTheDoctorChooseEffect());
        this.addAbility(sagaAbility);
    }

    private TheDayOfTheDoctor(final TheDayOfTheDoctor card) {
        super(card);
    }

    @Override
    public TheDayOfTheDoctor copy() {
        return new TheDayOfTheDoctor(this);
    }
}

class TheDayOfTheDoctorExileEffect extends OneShotEffect {

    TheDayOfTheDoctorExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a legendary card. " +
                "You may play that card for as long as {this} remains on the battlefield. " +
                "Put the rest of those exiled cards on the bottom of your library in a random order";
    }

    private TheDayOfTheDoctorExileEffect(final TheDayOfTheDoctorExileEffect effect) {
        super(effect);
    }

    @Override
    public TheDayOfTheDoctorExileEffect copy() {
        return new TheDayOfTheDoctorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, false,
                Duration.UntilSourceLeavesBattlefield, false
        );
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    private static Card getCard(Player player, Cards cards, Ability source, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (card.isLegendary(game)) {
                return card;
            }
            cards.add(card);
        }
        return null;
    }
}

class TheDayOfTheDoctorChooseEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DOCTOR, "Doctors");

    TheDayOfTheDoctorChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to three Doctors. You may exile all other creatures. " +
                "If you do, {this} deals 13 damage to you";
    }

    private TheDayOfTheDoctorChooseEffect(final TheDayOfTheDoctorChooseEffect effect) {
        super(effect);
    }

    @Override
    public TheDayOfTheDoctorChooseEffect copy() {
        return new TheDayOfTheDoctorChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 3, filter, true);
        player.choose(outcome, target, source, game);
        if (!player.chooseUse(outcome, "Exile all other creatures?", source, game)) {
            return false;
        }
        player.moveCards(
                game.getBattlefield()
                        .getActivePermanents(
                                StaticFilters.FILTER_PERMANENT_CREATURE,
                                source.getControllerId(), source, game
                        )
                        .stream()
                        .filter(permanent -> !target.getTargets()
                                .contains(permanent.getId()))
                        .collect(Collectors.toSet()),
                Zone.EXILED, source, game
        );
        player.damage(13, source, game);
        return true;
    }
}
