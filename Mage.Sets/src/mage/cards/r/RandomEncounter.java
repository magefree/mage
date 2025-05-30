package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RandomEncounter extends CardImpl {

    public RandomEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Shuffle your library, then mill four cards. Put each creature card milled this way onto the battlefield. They gain haste. At the beginning of the next end step, return those creatures to their owner's hand.
        this.getSpellAbility().addEffect(new RandomEncounterEffect());

        // Flashback {6}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{R}{R}")));
    }

    private RandomEncounter(final RandomEncounter card) {
        super(card);
    }

    @Override
    public RandomEncounter copy() {
        return new RandomEncounter(this);
    }
}

class RandomEncounterEffect extends OneShotEffect {

    RandomEncounterEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle your library, then mill four cards. Put each " +
                "creature card milled this way onto the battlefield. They gain haste. " +
                "At the beginning of the next end step, return those creatures to their owner's hand";
    }

    private RandomEncounterEffect(final RandomEncounterEffect effect) {
        super(effect);
    }

    @Override
    public RandomEncounterEffect copy() {
        return new RandomEncounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        player.shuffleLibrary(source, game);
        Cards cards = new CardsImpl(player.millCards(4, source, game).getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        Set<Permanent> permanents = cards
                .getCards(game)
                .stream()
                .map(card -> CardUtil.getPermanentFromCardPutToBattlefield(card, game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToHandTargetEffect()
                        .setText("return those creatures to their owner's hand")
                        .setTargetPointer(new FixedTargets(permanents, game))
        ), source);
        return true;
    }
}
