package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerrorOfThePeaks extends CardImpl {

    public TerrorOfThePeaks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast that target Terror of the Peaks cost an additional 3 life to cast.
        this.addAbility(new SimpleStaticAbility(new TerrorOfThePeaksCostIncreaseEffect()));

        // Whenever another creature enters the battlefield under your control, Terror of the Peaks deals damage equal to that creature's power to any target.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new DamageTargetEffect(TerrorOfThePeaksValue.instance).setText("{this} deals damage equal to that creature's power to any target"),
                StaticFilters.FILTER_ANOTHER_CREATURE
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TerrorOfThePeaks(final TerrorOfThePeaks card) {
        super(card);
    }

    @Override
    public TerrorOfThePeaks copy() {
        return new TerrorOfThePeaks(this);
    }
}

class TerrorOfThePeaksCostIncreaseEffect extends CostModificationEffectImpl {

    TerrorOfThePeaksCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spells your opponents cast that target {this} cost an additional 3 life to cast";
    }

    private TerrorOfThePeaksCostIncreaseEffect(TerrorOfThePeaksCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.addCost(new PayLifeCost(3));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }

        Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
        Set<UUID> allTargets;
        if (spell != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);

            // can target without cost increase
            if (allTargets.stream().anyMatch(target -> !isTargetCompatible(target, source, game))) {
                return false;
            }
        }

        return allTargets.stream().anyMatch(target -> isTargetCompatible(target, source, game));
    }

    private boolean isTargetCompatible(UUID target, Ability source, Game game) {
        // target {this}
        return Objects.equals(source.getSourceId(), target);
    }

    @Override
    public TerrorOfThePeaksCostIncreaseEffect copy() {
        return new TerrorOfThePeaksCostIncreaseEffect(this);
    }
}

enum TerrorOfThePeaksValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("permanentEnteringBattlefield");
        return permanent != null ? permanent.getPower().getValue() : 0;
    }

    @Override
    public TerrorOfThePeaksValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
