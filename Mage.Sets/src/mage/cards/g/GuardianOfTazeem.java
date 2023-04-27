
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class GuardianOfTazeem extends CardImpl {

    public GuardianOfTazeem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under you control, tap target creature an opponent controls. If that land is an Island, that creature doesn't untap during its controller's next untap step.
        Ability ability = new GuardianOfTazeemTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private GuardianOfTazeem(final GuardianOfTazeem card) {
        super(card);
    }

    @Override
    public GuardianOfTazeem copy() {
        return new GuardianOfTazeem(this);
    }
}

class GuardianOfTazeemTriggeredAbility extends TriggeredAbilityImpl {

    public GuardianOfTazeemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
        addEffect(new GuardianOfTazeemEffect());
        setTriggerPhrase("<i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, " );
    }

    public GuardianOfTazeemTriggeredAbility(final GuardianOfTazeemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GuardianOfTazeemTriggeredAbility copy() {
        return new GuardianOfTazeemTriggeredAbility(this);
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
            for (Effect effect : getEffects()) {
                if (effect instanceof GuardianOfTazeemEffect) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
            }
            return true;
        }
        return false;
    }
}

class GuardianOfTazeemEffect extends OneShotEffect {

    public GuardianOfTazeemEffect() {
        super(Outcome.Benefit);
        this.staticText = "If that land is an Island, that creature doesn't untap during its controller's next untap step";
    }

    public GuardianOfTazeemEffect(final GuardianOfTazeemEffect effect) {
        super(effect);
    }

    @Override
    public GuardianOfTazeemEffect copy() {
        return new GuardianOfTazeemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (land != null && targetCreature != null && land.hasSubtype(SubType.ISLAND, game)) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("that creature");
            effect.setTargetPointer(new FixedTarget(targetCreature, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
