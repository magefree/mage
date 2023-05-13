package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KopalaWardenOfWaves extends CardImpl {

    public KopalaWardenOfWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spells your opponents cast that target a Merfolk you control cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new KopalaWardenOfWavesCostModificationEffect1()));

        // Abilities your opponents activate that target a Merfolk you control cost {2} more to activate.
        this.addAbility(new SimpleStaticAbility(new KopalaWardenOfWavesCostModificationEffect2()));
    }

    private KopalaWardenOfWaves(final KopalaWardenOfWaves card) {
        super(card);
    }

    @Override
    public KopalaWardenOfWaves copy() {
        return new KopalaWardenOfWaves(this);
    }

    static boolean isTargetCompatible(Permanent permanent, Ability source, Game game) {
        // target a Merfolk you control
        return permanent.isControlledBy(source.getControllerId())
                && permanent.hasSubtype(SubType.MERFOLK, game);
    }

    static boolean isAbilityCompatible(Ability abilityToModify, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }

        Set<UUID> allTargets;
        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);

            // can target without cost increase
            if (allTargets.stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .anyMatch(permanent -> !isTargetCompatible(permanent, source, game))) {
                return false;
            }
            ;
        }

        return allTargets.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> isTargetCompatible(permanent, source, game));
    }
}

class KopalaWardenOfWavesCostModificationEffect1 extends CostModificationEffectImpl {

    KopalaWardenOfWavesCostModificationEffect1() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spells your opponents cast that target a Merfolk you control cost {2} more to cast";
    }

    KopalaWardenOfWavesCostModificationEffect1(KopalaWardenOfWavesCostModificationEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getAbilityType() == AbilityType.SPELL
                && KopalaWardenOfWaves.isAbilityCompatible(abilityToModify, source, game);
    }

    @Override
    public KopalaWardenOfWavesCostModificationEffect1 copy() {
        return new KopalaWardenOfWavesCostModificationEffect1(this);
    }

}

class KopalaWardenOfWavesCostModificationEffect2 extends CostModificationEffectImpl {

    KopalaWardenOfWavesCostModificationEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Abilities your opponents activate that target a Merfolk you control cost {2} more to activate";
    }

    KopalaWardenOfWavesCostModificationEffect2(KopalaWardenOfWavesCostModificationEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                && KopalaWardenOfWaves.isAbilityCompatible(abilityToModify, source, game);
    }

    @Override
    public KopalaWardenOfWavesCostModificationEffect2 copy() {
        return new KopalaWardenOfWavesCostModificationEffect2(this);
    }

}
