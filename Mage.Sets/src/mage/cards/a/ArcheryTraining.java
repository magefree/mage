package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ArcheryTraining extends CardImpl {

    public ArcheryTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, you may put an arrow counter on Archery Training.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(
                CounterType.ARROW.createInstance(), true
        ), TargetController.YOU, true));

        // Enchanted creature has "{tap}: This creature deals X damage to target attacking or blocking creature, where X is the number of arrow counters on Archery Training."
        this.addAbility(new SimpleStaticAbility(new ArcheryTrainingEffect()));
    }

    private ArcheryTraining(final ArcheryTraining card) {
        super(card);
    }

    @Override
    public ArcheryTraining copy() {
        return new ArcheryTraining(this);
    }
}

class ArcheryTrainingEffect extends ContinuousEffectImpl {

    ArcheryTrainingEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "enchanted creature has \"{T}: This creature deals X damage " +
                "to target attacking or blocking creature, where X is the number of arrow counters on {this}.\"";
    }

    private ArcheryTrainingEffect(final ArcheryTrainingEffect effect) {
        super(effect);
    }

    @Override
    public ArcheryTrainingEffect copy() {
        return new ArcheryTrainingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        String rule = "this creature deals X damage to target attacking or blocking creature, " +
                "where X is the number of arrow counters on " + aura.getName();
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(new ArcheryTrainingValue(aura)).setText(rule), new TapSourceCost()
        );
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }
}

class ArcheryTrainingValue implements DynamicValue {

    private final Permanent permanent;

    ArcheryTrainingValue(Permanent permanent) {
        this.permanent = permanent;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return permanent == null ? 0 : permanent.getCounters(game).getCount(CounterType.ARROW);
    }

    @Override
    public ArcheryTrainingValue copy() {
        return new ArcheryTrainingValue(this.permanent);
    }

    @Override
    public String getMessage() {
        return "";
    }
}
