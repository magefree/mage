package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheWarInHeaven extends CardImpl {

    public TheWarInHeaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You draw three cards and you lose 3 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(3)
                        .setText("you draw three cards"),
                new LoseLifeSourceControllerEffect(3)
                        .concatBy("and")
        );

        // II -- Mill three cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new MillCardsControllerEffect(3));

        // III -- Choose up to three target creature cards with total mana value 8 or less in your graveyard. Return each of them to the battlefield with a necrodermis counter on it. They're artifacts in addition to their other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new TheWarInHeavenEffect(), new TheWarInHeavenTarget()
        );
        this.addAbility(sagaAbility);
    }

    private TheWarInHeaven(final TheWarInHeaven card) {
        super(card);
    }

    @Override
    public TheWarInHeaven copy() {
        return new TheWarInHeaven(this);
    }
}

class TheWarInHeavenEffect extends OneShotEffect {

    TheWarInHeavenEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to three target creature cards with total mana value 8 or less in your graveyard. " +
                "Return each of them to the battlefield with a necrodermis counter on it. " +
                "They're artifacts in addition to their other types";
    }

    private TheWarInHeavenEffect(final TheWarInHeavenEffect effect) {
        super(effect);
    }

    @Override
    public TheWarInHeavenEffect copy() {
        return new TheWarInHeavenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.NECRODERMIS.createInstance()
        ).apply(game, source);
        List<Permanent> permanents = cards
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!permanents.isEmpty()) {
            game.addEffect(new AddCardTypeTargetEffect(
                    Duration.Custom, CardType.ARTIFACT
            ).setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        return true;
    }
}

class TheWarInHeavenTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards with total mana value 8 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 9));
    }

    TheWarInHeavenTarget() {
        super(0, 3, filter, false);
    }

    private TheWarInHeavenTarget(final TheWarInHeavenTarget target) {
        super(target);
    }

    @Override
    public TheWarInHeavenTarget copy() {
        return new TheWarInHeavenTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null &&
                this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .mapToInt(Card::getManaValue)
                        .sum() + card.getManaValue() <= 8;
    }
}
