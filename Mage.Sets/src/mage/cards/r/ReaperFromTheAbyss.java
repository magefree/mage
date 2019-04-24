

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.common.MorbidWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ReaperFromTheAbyss extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.DEMON)));
    }

    public ReaperFromTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new ReaperFromTheAbyssAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public ReaperFromTheAbyss(final ReaperFromTheAbyss card) {
        super(card);
    }

    @Override
    public ReaperFromTheAbyss copy() {
        return new ReaperFromTheAbyss(this);
    }

}

class ReaperFromTheAbyssAbility extends TriggeredAbilityImpl {

    public ReaperFromTheAbyssAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    public ReaperFromTheAbyssAbility(final ReaperFromTheAbyssAbility ability) {
        super(ability);
    }

    @Override
    public ReaperFromTheAbyssAbility copy() {
        return new ReaperFromTheAbyssAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Watcher watcher = game.getState().getWatcher(MorbidWatcher.class);
        return  watcher != null && watcher.conditionMet();
    }

    @Override
    public String getRule() {
        return "<i>Morbid</i> &mdash; At the beginning of each end step, if a creature died this turn, destroy target non-demon creature.";
    }
}
