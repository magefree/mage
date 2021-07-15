
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
/**
 *
 * @author L_J
 */
public final class FrostwebSpider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public FrostwebSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Frostweb Spider blocks a creature with flying, put a +1/+1 counter on Frostweb Spider at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())), true);
        effect.setText("put a +1/+1 counter on {this} at end of combat");
        this.addAbility(new FrostwebSpiderTriggeredAbility(effect, filter, false));
    }

    private FrostwebSpider(final FrostwebSpider card) {
        super(card);
    }

    @Override
    public FrostwebSpider copy() {
        return new FrostwebSpider(this);
    }
}

class FrostwebSpiderTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;

    public FrostwebSpiderTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public FrostwebSpiderTriggeredAbility(final FrostwebSpiderTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent blocked = game.getPermanent(event.getTargetId());
            if (blocked != null && filter.match(blocked, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks a " + filter.getMessage() + ", " ;
    }

    @Override
    public FrostwebSpiderTriggeredAbility copy() {
        return new FrostwebSpiderTriggeredAbility(this);
    }
}
