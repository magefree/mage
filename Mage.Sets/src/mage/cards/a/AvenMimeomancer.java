
package mage.cards.a;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

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
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersTargetEffect(CounterType.FEATHER.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new AvenEffect());
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

    AvenEffect() {
        super(Duration.Custom, Outcome.BoostCreature);
        this.staticText = "If you do, that creature has base power and toughness 3/1 and has flying for as long as it has a feather counter on it";
    }

    private AvenEffect(final AvenEffect effect) {
        super(effect);
    }

    @Override
    public AvenEffect copy() {
        return new AvenEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(3);
                        permanent.getToughness().setModifiedBaseValue(1);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target == null || target.getCounters(game).getCount(CounterType.FEATHER) < 1) {
            this.discard();
            return false;
        }
        affectedObjects.add(target);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.PTChangingEffects_7;
    }
}
