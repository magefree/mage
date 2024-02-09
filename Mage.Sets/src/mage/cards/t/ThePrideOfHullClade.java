package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class ThePrideOfHullClade extends CardImpl {

    public ThePrideOfHullClade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(15);

        // This spell costs {X} less to cast, where X is the total toughness of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ThePrideOfHullCladeCostReductionEffect()));

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{U}{U}: Until end of turn, target creature you control gets +1/+0, gains "Whenever this creature deals combat damage to a player, draw cards equal to its toughness," and can attack as though it didn't have defender.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 0)
                        .setText("Until end of turn, target creature you control gets +1/0"),
                new ManaCostsImpl<>("{2}{U}{U}"))
                ;
        ability.addEffect(
                new GainAbilityTargetEffect(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new ThePrideOfHullCladeEffect(), false))
                        .setText(", gains \"Whenever this creature deals combat damage to a player, draw cards equal to its toughness,\""));
        ability.addEffect(
                new CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration.EndOfTurn)
                        .setText("and can attack as though it didn't have defender."));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ThePrideOfHullClade(final ThePrideOfHullClade card) {
        super(card);
    }

    @Override
    public ThePrideOfHullClade copy() {
        return new ThePrideOfHullClade(this);
    }
}

class ThePrideOfHullCladeCostReductionEffect extends CostModificationEffectImpl {

    ThePrideOfHullCladeCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is the total toughness of creatures you control";
    }

    private ThePrideOfHullCladeCostReductionEffect(final ThePrideOfHullCladeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public ThePrideOfHullCladeCostReductionEffect copy() {
        return new ThePrideOfHullCladeCostReductionEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, abilityToModify.getControllerId(), game)
                .stream()
                .map(Permanent::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
        CardUtil.reduceCost(abilityToModify, Math.max(0, reductionAmount));
        return true;
    }
}

class ThePrideOfHullCladeEffect extends OneShotEffect {

    ThePrideOfHullCladeEffect() {
        super(Outcome.DrawCard);
        staticText = "draw cards equal to its toughness";
    }

    private ThePrideOfHullCladeEffect(final ThePrideOfHullCladeEffect effect) {
        super(effect);
    }

    @Override
    public ThePrideOfHullCladeEffect copy() {
        return new ThePrideOfHullCladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }

        int toughness = permanent.getToughness().getValue();
        if (toughness > 0) {
            controller.drawCards(toughness, source, game);
            return true;
        }
        return false;
    }
}
