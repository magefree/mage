package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Arketec
 */
public final class HinataDawnCrowned extends CardImpl {

    public HinataDawnCrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIRIN);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Spells you cast cost {1} less to cast for each target.
        this.addAbility(new SimpleStaticAbility(new HinataDawnCrownedOwnEffect()));

        // Spells your opponents cast cost {1} more to cast for each target
        this.addAbility(new SimpleStaticAbility(new HinataDawnCrownedOpponentsEffect()));
    }

    private HinataDawnCrowned(final HinataDawnCrowned card) {
        super(card);
    }

    @Override
    public HinataDawnCrowned copy() {
        return new HinataDawnCrowned(this);
    }
}

class HinataDawnCrownedOwnEffect extends CostModificationEffectImpl {

    HinataDawnCrownedOwnEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.REDUCE_COST);
        staticText = "Spells you cast cost {1} less to cast for each target";
    }

    private HinataDawnCrownedOwnEffect(HinataDawnCrownedOwnEffect effect) {
        super(effect);
    }

    @Override
    public HinataDawnCrownedOwnEffect copy() {
        return new HinataDawnCrownedOwnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, HinataDawnCrownedEffectUtility.getTargetCount(game, abilityToModify));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId());
    }
}

class HinataDawnCrownedOpponentsEffect extends CostModificationEffectImpl {

    HinataDawnCrownedOpponentsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast cost {1} more to cast for each target";
    }

    private HinataDawnCrownedOpponentsEffect(HinataDawnCrownedOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public HinataDawnCrownedOpponentsEffect copy() {
        return new HinataDawnCrownedOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, HinataDawnCrownedEffectUtility.getTargetCount(game, abilityToModify));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && !abilityToModify.isControlledBy(source.getControllerId());
    }
}

final class HinataDawnCrownedEffectUtility
{
    public static int getTargetCount(Game game, Ability abilityToModify)
    {
        return (int)(game.inCheckPlayableState() ?
                CardUtil.getAllPossibleTargets(abilityToModify, game).stream().count():
                CardUtil.getAllSelectedTargets(abilityToModify, game).stream().count());
    }
}
