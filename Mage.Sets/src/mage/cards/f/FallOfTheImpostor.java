package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class FallOfTheImpostor extends CardImpl {

    public FallOfTheImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put a +1/+1 counter on up to one target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetCreaturePermanent(0, 1)
        );

        // III — Exile a creature with the greatest power among creatures target opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new FallOfTheImpostorEffect(), new TargetOpponent()
        );
        this.addAbility(sagaAbility);
    }

    private FallOfTheImpostor(final FallOfTheImpostor card) {
        super(card);
    }

    @Override
    public FallOfTheImpostor copy() {
        return new FallOfTheImpostor(this);
    }
}

class FallOfTheImpostorEffect extends OneShotEffect {

    FallOfTheImpostorEffect() {
        super(Outcome.Exile);
        staticText = "Exile a creature with the greatest power among creatures target opponent controls";
    }

    private FallOfTheImpostorEffect(final FallOfTheImpostorEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheImpostorEffect copy() {
        return new FallOfTheImpostorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, opponent.getId(), game
        );
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                permanent = permanents.get(0);
                break;
            default:
                int power = permanents
                        .stream()
                        .map(MageObject::getPower)
                        .mapToInt(MageInt::getValue)
                        .max()
                        .orElse(Integer.MIN_VALUE);
                FilterPermanent filter = new FilterCreaturePermanent();
                filter.add(new ControllerIdPredicate(opponent.getId()));
                filter.add(new PowerPredicate(ComparisonType.OR_GREATER, power));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                controller.chooseTarget(outcome, target, source, game);
                permanent = game.getPermanent(target.getFirstTarget());
        }
        return permanent != null && controller.moveCards(permanent, Zone.EXILED, source, game);
    }
}
