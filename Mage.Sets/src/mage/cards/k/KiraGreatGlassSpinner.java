package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.NumberOfTimesPermanentTargetedATurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class KiraGreatGlassSpinner extends CardImpl {

    public KiraGreatGlassSpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability."
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell or ability");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new KiraGreatGlassSpinnerAbility(effect), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES)),
                new NumberOfTimesPermanentTargetedATurnWatcher());

    }

    private KiraGreatGlassSpinner(final KiraGreatGlassSpinner card) {
        super(card);
    }

    @Override
    public KiraGreatGlassSpinner copy() {
        return new KiraGreatGlassSpinner(this);
    }
}

class KiraGreatGlassSpinnerAbility extends TriggeredAbilityImpl {

    public KiraGreatGlassSpinnerAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public KiraGreatGlassSpinnerAbility(final KiraGreatGlassSpinnerAbility ability) {
        super(ability);
    }

    @Override
    public KiraGreatGlassSpinnerAbility copy() {
        return new KiraGreatGlassSpinnerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                NumberOfTimesPermanentTargetedATurnWatcher watcher = game.getState().getWatcher(NumberOfTimesPermanentTargetedATurnWatcher.class);
                if (watcher != null && watcher.notMoreThanOnceTargetedThisTurn(permanent, game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability.";
    }

}
