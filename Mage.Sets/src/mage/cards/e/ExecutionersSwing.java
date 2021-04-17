package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 *
 * @author LevelX2
 */
public final class ExecutionersSwing extends CardImpl {

    private static final FilterPermanent filter=new FilterCreaturePermanent("creature that dealt damage this turn");static {filter.add(ExecutionersSwingPredicate.instance);}
    public ExecutionersSwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Target creature that dealt damage this turn gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());
    }

    private ExecutionersSwing(final ExecutionersSwing card) {
        super(card);
    }

    @Override
    public ExecutionersSwing copy() {
        return new ExecutionersSwing(this);
    }
}
enum ExecutionersSwingPredicate implements Predicate<Permanent>{
   instance ;

    @Override
    public boolean apply(Permanent input, Game game) {
        SourceDidDamageWatcher watcher=game.getState().getWatcher(SourceDidDamageWatcher.class);
        return watcher!=null&&watcher.checkSource(input,game);
    }
}