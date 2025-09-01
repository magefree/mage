package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
                new DrawCardSourceControllerEffect(3, true),
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
        staticText = "choose up to three target creature cards with total mana value 8 or less in your graveyard. "
                + "Return each of them to the battlefield with a necrodermis counter on it. "
                + "They're artifacts in addition to their other types";
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
        List<UUID> targetIds = this.getTargetPointer().getTargets(game, source);
        for (UUID targetId : targetIds) {
            Card card = game.getCard(targetId);
            if (card != null) {
                card.moveToZone(Zone.BATTLEFIELD, source, game, false);
                Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
                if (permanent != null) {
                    permanent.addCounters(CounterType.NECRODERMIS.createInstance(), source, game);
                    game.addEffect(new AddCardTypeTargetEffect(Duration.Custom, CardType.ARTIFACT)
                            .setTargetPointer(new FixedTarget(permanent, game)), source);
                }
            }
        }
        return true;
    }
}

class TheWarInHeavenTarget extends TargetCardInYourGraveyard {

    private static final FilterCreatureCard filterStatic
            = new FilterCreatureCard("creature cards with total mana value 8 or less from your graveyard");

    TheWarInHeavenTarget() {
        super(0, 3, filterStatic, false);
    }

    private TheWarInHeavenTarget(final TheWarInHeavenTarget target) {
        super(target);
    }

    @Override
    public TheWarInHeavenTarget copy() {
        return new TheWarInHeavenTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 8, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 8, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
