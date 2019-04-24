
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AdarkarValkyrie extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public AdarkarValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: When target creature other than Adarkar Valkyrie dies this turn, return that card to the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AdarkarValkyrieEffect(), new TapSourceCost());

        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public AdarkarValkyrie(final AdarkarValkyrie card) {
        super(card);
    }

    @Override
    public AdarkarValkyrie copy() {
        return new AdarkarValkyrie(this);
    }
}

class AdarkarValkyrieEffect extends OneShotEffect {

    public AdarkarValkyrieEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When target creature other than {this} dies this turn, return that card to the battlefield under your control";
    }

    public AdarkarValkyrieEffect(final AdarkarValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public AdarkarValkyrieEffect copy() {
        return new AdarkarValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            DelayedTriggeredAbility delayedAbility = new AdarkarValkyrieDelayedTriggeredAbility(new MageObjectReference(permanent, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class AdarkarValkyrieDelayedTriggeredAbility extends DelayedTriggeredAbility {

    protected MageObjectReference mor;

    public AdarkarValkyrieDelayedTriggeredAbility(MageObjectReference mor) {
        super(new ReturnToBattlefieldUnderYourControlTargetEffect(), Duration.EndOfTurn);
        this.mor = mor;
    }

    public AdarkarValkyrieDelayedTriggeredAbility(final AdarkarValkyrieDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public AdarkarValkyrieDelayedTriggeredAbility copy() {
        return new AdarkarValkyrieDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()
                && mor.refersTo(((ZoneChangeEvent) event).getTarget(), game)) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;

        }
        return false;
    }

    @Override
    public String getRule() {
        return "When target creature other than {this} dies this turn, " + super.getRule();
    }
}
