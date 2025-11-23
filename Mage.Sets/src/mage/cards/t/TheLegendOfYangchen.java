package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfYangchen extends CardImpl {

    public TheLegendOfYangchen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.AvatarYangchen.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Starting with you, each player chooses up to one permanent with mana value 3 or greater from among permanents your opponents control. Exile those permanents.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheLegendOfYangchenEffect());

        // II -- You may have target opponent draw three cards. If you do, draw three cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(
                        new DrawCardTargetEffect(3).setText("have target opponent draw three cards. If you do"),
                        new DrawCardSourceControllerEffect(3).concatBy(",")
                ), new TargetOpponent(), true
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.addAbility(sagaAbility);
    }

    private TheLegendOfYangchen(final TheLegendOfYangchen card) {
        super(card);
    }

    @Override
    public TheLegendOfYangchen copy() {
        return new TheLegendOfYangchen(this);
    }
}

class TheLegendOfYangchenEffect extends OneShotEffect {

    TheLegendOfYangchenEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player chooses up to one permanent with mana value 3 or greater " +
                "from among permanents your opponents control. Exile those permanents";
    }

    private TheLegendOfYangchenEffect(final TheLegendOfYangchenEffect effect) {
        super(effect);
    }

    @Override
    public TheLegendOfYangchenEffect copy() {
        return new TheLegendOfYangchenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
        filter.add(Predicates.not(new ControllerIdPredicate(source.getControllerId())));
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 1, filter, true);
            player.choose(Outcome.Exile, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        permanents.removeIf(Objects::isNull);
        if (permanents.isEmpty()) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(permanents, Zone.EXILED, source, game);
    }
}
