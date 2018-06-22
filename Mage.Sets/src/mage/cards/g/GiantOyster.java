package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noahg
 */
public final class GiantOyster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new TappedPredicate());
    }

    public GiantOyster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.subtype.add(SubType.OYSTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // You may choose not to untap Giant Oyster during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: For as long as Giant Oyster remains tapped, target tapped creature doesn't untap during its controller's untap step, and at the beginning of each of your draw steps, put a -1/-1 counter on that creature. When Giant Oyster leaves the battlefield or becomes untapped, remove all -1/-1 counters from the creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DontUntapAsLongAsSourceTappedEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public GiantOyster(final GiantOyster card) {
        super(card);
    }

    @Override
    public GiantOyster copy() {
        return new GiantOyster(this);
    }
}

class GiantOysterDrawStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DRAW_STEP_PRE);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId()) && game.getPermanent(getSourcePermanentIfItStillExists())){
            return true;
        }
        return false;
    }

    @Override
    public DelayedTriggeredAbility copy() {
        return null;
    }
}