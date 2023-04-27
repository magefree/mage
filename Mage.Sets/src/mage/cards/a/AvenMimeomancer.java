
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AvenMimeomancer extends CardImpl {

    public AvenMimeomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may put a feather counter on target creature. If you do, that creature has base power and toughness 3/1 and has flying for as long as it has a feather counter on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FEATHER.createInstance()), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new AvenEffect());
        ability.addEffect(new AvenEffect2());
        this.addAbility(ability);
    }

    private AvenMimeomancer(final AvenMimeomancer card) {
        super(card);
    }

    @Override
    public AvenMimeomancer copy() {
        return new AvenMimeomancer(this);
    }
}

class AvenEffect extends ContinuousEffectImpl {

    public AvenEffect() {
        super(Duration.Custom, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.staticText = "If you do, that creature has base power and toughness 3/1 and has flying for as long as it has a feather counter on it";
    }

    public AvenEffect(final AvenEffect effect) {
        super(effect);
    }

    @Override
    public AvenEffect copy() {
        return new AvenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target == null) {
            return false;
        }
        target.getPower().setModifiedBaseValue(3);
        target.getToughness().setModifiedBaseValue(1);
        return true;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null && creature.getCounters(game).getCount(CounterType.FEATHER) < 1) {
            return true;
        }
        return false;
    }
}

class AvenEffect2 extends ContinuousEffectImpl {

    public AvenEffect2() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.BoostCreature);
    }

    public AvenEffect2(final AvenEffect2 effect) {
        super(effect);
    }

    @Override
    public AvenEffect2 copy() {
        return new AvenEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            if (!target.getAbilities().contains(FlyingAbility.getInstance())) {
                target.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null && creature.getCounters(game).getCount(CounterType.FEATHER) < 1) {
            return true;
        }
        return false;
    }
}