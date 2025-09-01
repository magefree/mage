package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirionWildRoseWarrior extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("equipped creatures");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.EQUIPMENT, "a nontoken Equipment you control");

    static {
        filter.add(EquippedPredicate.instance);
        filter2.add(TokenPredicate.FALSE);
    }

    public FirionWildRoseWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Equipped creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever a nontoken Equipment you control enters, create a token that's a copy of it, except it has "This Equipment's equip abilities cost {2} less to activate." Sacrifice that token at the beginning of the next upkeep.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new FirionWildRoseWarriorEffect(), filter2));
    }

    private FirionWildRoseWarrior(final FirionWildRoseWarrior card) {
        super(card);
    }

    @Override
    public FirionWildRoseWarrior copy() {
        return new FirionWildRoseWarrior(this);
    }
}

class FirionWildRoseWarriorEffect extends OneShotEffect {

    FirionWildRoseWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it, except it has " +
                "\"This Equipment's equip abilities cost {2} less to activate.\" " +
                "Sacrifice that token at the beginning of the next upkeep";
    }

    private FirionWildRoseWarriorEffect(final FirionWildRoseWarriorEffect effect) {
        super(effect);
    }

    @Override
    public FirionWildRoseWarriorEffect copy() {
        return new FirionWildRoseWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.addAdditionalAbilities(new SimpleStaticAbility(new FirionWildRoseWarriorReductionEffect()));
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}

class FirionWildRoseWarriorReductionEffect extends CostModificationEffectImpl {

    FirionWildRoseWarriorReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this Equipment's equip abilities cost {2} less to activate";
    }

    private FirionWildRoseWarriorReductionEffect(final FirionWildRoseWarriorReductionEffect effect) {
        super(effect);
    }

    @Override
    public FirionWildRoseWarriorReductionEffect copy() {
        return new FirionWildRoseWarriorReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof EquipAbility
                && source.getSourceId().equals(abilityToModify.getSourceId());
    }
}
