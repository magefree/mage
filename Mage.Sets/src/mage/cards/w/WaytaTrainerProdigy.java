package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.BatchGameEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;


/**
 *
 * @author jimga150
 */
public final class WaytaTrainerProdigy extends CardImpl {

    public WaytaTrainerProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}{G}, {T}: Target creature you control fights another target creature. This ability costs {2} less to activate if it targets two creatures you control.
        // Based on Ulvenwald Tracker
        Ability ability = new SimpleActivatedAbility(new FightTargetsEffect(false), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {2} less to activate if it targets two creatures you control."));
        ability.setCostAdjuster(WaytaTrainerProdigyAdjuster.instance);

        Target controlledTarget = new TargetControlledCreaturePermanent().setTargetTag(1);
        ability.addTarget(controlledTarget);

        Target secondTarget =
                new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2);
        ability.addTarget(secondTarget);

        this.addAbility(ability);
        
        // If a creature you control being dealt damage causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        // Based on Isshin, Two Heavens as One
        this.addAbility(new SimpleStaticAbility(new WaytaTrainerProdigyEffect()));

        // Rulings:

        // Wayta, Trainer Prodigy's last ability affects only triggered abilities whose trigger conditions refer
        // specifically to damage being dealt, such as the ability granted by Mephidross Vampire or the last ability
        // of Wrathful Raptors. It does not affect triggered abilities that would trigger because of the results of
        // that damage. For example, if you control an Ajani's Pridemate ("Whenever you gain life, put a +1/+1 counter
        // on Ajani's Pridemate") and you activate Wayta, Trainer Prodigy's second ability targeting a creature you
        // control with lifelink and another creature you control, the triggered ability of Ajani's Pridemate will
        // still trigger only once.
        // (2023-11-10)

        // If you somehow control two copies of Wayta, Trainer Prodigy, a creature you control being dealt damage
        // causes abilities to trigger three times, not four. A third Wayta, Trainer Prodigy causes abilities to
        // trigger four times, a fourth causes abilities to trigger five times, and so on.
        // (2023-11-10)
    }

    private WaytaTrainerProdigy(final WaytaTrainerProdigy card) {
        super(card);
    }

    @Override
    public WaytaTrainerProdigy copy() {
        return new WaytaTrainerProdigy(this);
    }
}

// Based on Crown of Gondor and SourceTargetsPermanentCondition
enum WaytaTrainerProdigyAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Target secondTarget = null;
        for (Target target : ability.getTargets()){
            if (target.getTargetTag() == 2){
                secondTarget = target;
                break;
            }
        }
        if (secondTarget == null){
            return;
        }
        // Having to call getFirstTarget() on a Target object called secondTarget
        // (because it's the second target of a two-target ability)
        // seems like an insult, but this is just getting the UUID of that target
        Permanent permanent = game.getPermanentOrLKIBattlefield(secondTarget.getFirstTarget());
        if (permanent != null && permanent.isControlledBy(ability.getControllerId())) {
            CardUtil.reduceCost(ability, 2);
        }
    }
}

class WaytaTrainerProdigyEffect extends ReplacementEffectImpl {

    WaytaTrainerProdigyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control being dealt damage causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private WaytaTrainerProdigyEffect(final WaytaTrainerProdigyEffect effect) {
        super(effect);
    }

    @Override
    public WaytaTrainerProdigyEffect copy() {
        return new WaytaTrainerProdigyEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {

        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;

        // Permanent whose ability is being triggered (and will be retriggered)
        Permanent triggeredPermanent = game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId());
        if (triggeredPermanent == null || !triggeredPermanent.isControlledBy(source.getControllerId())) {
            return false;
        }

        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }

        if (!(sourceEvent.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PERMANENTS
                || sourceEvent.getType() == GameEvent.EventType.DAMAGED_PERMANENT)){
            return false;
        }

        // Get the one or more permanents damaged in this event
        List<UUID> damagedPermanents = new ArrayList<>();
        if (sourceEvent instanceof BatchGameEvent) {
            damagedPermanents.addAll(((BatchGameEvent<?>) sourceEvent).getTargets());
        } else {
            damagedPermanents.add(sourceEvent.getTargetId());
        }

        // If none of them are controlled by you, this ability doesn't apply.
        return damagedPermanents
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.isControlledBy(source.getControllerId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
