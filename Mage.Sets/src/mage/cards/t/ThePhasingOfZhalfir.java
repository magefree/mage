package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianToken;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThePhasingOfZhalfir extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ThePhasingOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I, II -- Another target nonland permanent phases out. It can't phase in for as long as you control The Phasing of Zhalfir.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new PhaseOutTargetEffect("another target nonland permanent"),
                        new ThePhasingOfZhalfirPhaseEffect()
                ), new TargetPermanent(filter)
        );

        // III -- Destroy all creatures. For each creature destroyed this way, its controller creates a 2/2 black Phyrexian creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ThePhasingOfZhalfirDestroyEffect());
        this.addAbility(sagaAbility);
    }

    private ThePhasingOfZhalfir(final ThePhasingOfZhalfir card) {
        super(card);
    }

    @Override
    public ThePhasingOfZhalfir copy() {
        return new ThePhasingOfZhalfir(this);
    }
}

class ThePhasingOfZhalfirPhaseEffect extends ContinuousRuleModifyingEffectImpl {

    ThePhasingOfZhalfirPhaseEffect() {
        super(Duration.WhileControlled, Outcome.Neutral);
        staticText = "It can't phase in for as long as you control {this}";
    }

    private ThePhasingOfZhalfirPhaseEffect(final ThePhasingOfZhalfirPhaseEffect effect) {
        super(effect);
    }

    @Override
    public ThePhasingOfZhalfirPhaseEffect copy() {
        return new ThePhasingOfZhalfirPhaseEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(this.getTargetPointer().getFirst(game, source));
    }
}

class ThePhasingOfZhalfirDestroyEffect extends OneShotEffect {

    ThePhasingOfZhalfirDestroyEffect() {
        super(Outcome.Benefit);
        staticText = "destroy all creatures. For each creature destroyed this way, " +
                "its controller creates a 2/2 black Phyrexian creature token";
    }

    private ThePhasingOfZhalfirDestroyEffect(final ThePhasingOfZhalfirDestroyEffect effect) {
        super(effect);
    }

    @Override
    public ThePhasingOfZhalfirDestroyEffect copy() {
        return new ThePhasingOfZhalfirDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerMap = new HashMap<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURES,
                source.getControllerId(), source, game
        )) {
            if (permanent.destroy(source, game, false)) {
                playerMap.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
            }
        }
        game.getState().processAction(game);
        Token token = new PhyrexianToken();
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            token.putOntoBattlefield(entry.getValue(), game, source, entry.getKey());
        }
        return true;
    }
}
