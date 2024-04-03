package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OmenpathJourney extends CardImpl {

    public OmenpathJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When Omenpath Journey enters the battlefield, search your library for up to five land cards that have different names, exile them, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OmenpathJourneySearchEffect()));

        // At the beginning of your end step, choose a card at random exiled with Omenpath Journey and put it onto the battlefield tapped.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new OmenpathJourneyChooseEffect(), TargetController.YOU, false
        ));
    }

    private OmenpathJourney(final OmenpathJourney card) {
        super(card);
    }

    @Override
    public OmenpathJourney copy() {
        return new OmenpathJourney(this);
    }
}

class OmenpathJourneySearchEffect extends OneShotEffect {

    OmenpathJourneySearchEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to five land cards that have different names, exile them, then shuffle";
    }

    private OmenpathJourneySearchEffect(final OmenpathJourneySearchEffect effect) {
        super(effect);
    }

    @Override
    public OmenpathJourneySearchEffect copy() {
        return new OmenpathJourneySearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardWithDifferentNameInLibrary(
                0, 5, StaticFilters.FILTER_CARD_LAND
        );
        player.searchLibrary(target, source, game);
        Set<Card> cards = target
                .getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        player.moveCardsToExile(
                cards, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}

class OmenpathJourneyChooseEffect extends OneShotEffect {

    OmenpathJourneyChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose a card at random exiled with {this} and put it onto the battlefield tapped";
    }

    private OmenpathJourneyChooseEffect(final OmenpathJourneyChooseEffect effect) {
        super(effect);
    }

    @Override
    public OmenpathJourneyChooseEffect copy() {
        return new OmenpathJourneyChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null
                && exileZone != null
                && !exileZone.isEmpty()
                && player.moveCards(
                exileZone.getRandom(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
    }
}
