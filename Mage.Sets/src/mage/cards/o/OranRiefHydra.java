package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class OranRiefHydra extends CardImpl {

    public OranRiefHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield 
        // under your control, put a +1/+1 counter on Oran-Rief Hydra.
        // If that land is a Forest, put two +1/+1 counters on Oran-Rief Hydra instead.
        this.addAbility(new OranRiefHydraTriggeredAbility());
    }

    private OranRiefHydra(final OranRiefHydra card) {
        super(card);
    }

    @Override
    public OranRiefHydra copy() {
        return new OranRiefHydra(this);
    }
}

class OranRiefHydraTriggeredAbility extends TriggeredAbilityImpl {

    private static final String text = "<i>Landfall</i> &mdash; Whenever a "
            + "land enters the battlefield under your control, put a +1/+1 counter on {this}. "
            + "If that land is a Forest, put two +1/+1 counters on {this} instead.";

    public OranRiefHydraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OranRiefHydraEffect());
    }

    private OranRiefHydraTriggeredAbility(final OranRiefHydraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OranRiefHydraTriggeredAbility copy() {
        return new OranRiefHydraTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isLand(game)
                && permanent.isControlledBy(getControllerId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null) {
                for (Effect effect : getEffects()) {
                    if (effect instanceof OranRiefHydraEffect) {
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return text;
    }
}

class OranRiefHydraEffect extends OneShotEffect {

    public OranRiefHydraEffect() {
        super(Outcome.BoostCreature);
    }

    private OranRiefHydraEffect(final OranRiefHydraEffect effect) {
        super(effect);
    }

    @Override
    public OranRiefHydraEffect copy() {
        return new OranRiefHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // the LKI of the land to verify the last-known land type
        Permanent landLKI = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Permanent land = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        // the land must be on the battlefield when the trigger resolves
        if (land != null
                && landLKI != null
                && sourcePermanent != null) {
            if (landLKI.hasSubtype(SubType.FOREST, game)) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
            } else {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }
}
