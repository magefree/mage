package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheHorusHeresy extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Creatures you control but don't own", xValue);

    public TheHorusHeresy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{B}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each opponent, gain control of up to one target nonlegendary creature that player controls for as long as The Horus Heresy remains on the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new TheHorusHeresyControlEffect());
            ability.setTargetAdjuster(TheHorusHeresyAdjuster.instance);
        });

        // II -- Draw a card for each creature you control but don't own.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DrawCardSourceControllerEffect(xValue));

        // III -- Starting with you, each player chooses a creature. Destroy each creature chosen this way.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheHorusHeresyDestroyEffect());
        this.addAbility(sagaAbility.addHint(hint));
    }

    private TheHorusHeresy(final TheHorusHeresy card) {
        super(card);
    }

    @Override
    public TheHorusHeresy copy() {
        return new TheHorusHeresy(this);
    }
}

enum TheHorusHeresyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("nonlegendary creature controlled by " + player.getName());
            filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class TheHorusHeresyControlEffect extends GainControlTargetEffect {

    TheHorusHeresyControlEffect() {
        super(Duration.Custom);
        staticText = "for each opponent, gain control of up to one target nonlegendary creature " +
                "that player controls for as long as {this} remains on the battlefield";
        this.setTargetPointer(new EachTargetPointer());
    }

    private TheHorusHeresyControlEffect(final TheHorusHeresyControlEffect effect) {
        super(effect);
    }

    @Override
    public TheHorusHeresyControlEffect copy() {
        return new TheHorusHeresyControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getSourcePermanentIfItStillExists(game) != null && super.apply(game, source);
    }
}

class TheHorusHeresyDestroyEffect extends OneShotEffect {

    TheHorusHeresyDestroyEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player chooses a creature. Destroy each creature chosen this way";
    }

    private TheHorusHeresyDestroyEffect(final TheHorusHeresyDestroyEffect effect) {
        super(effect);
    }

    @Override
    public TheHorusHeresyDestroyEffect copy() {
        return new TheHorusHeresyDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().count(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        ) < 1) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetCreaturePermanent();
            target.setNotTarget(true);
            player.choose(Outcome.DestroyPermanent, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanents.add(permanent);
                game.informPlayers(player.getLogName() + " chooses " + permanent.getLogName());
            }
        }
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
