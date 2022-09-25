package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public final class AngelOfJubilation extends CardImpl {

    public AngelOfJubilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other nonblack creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, true)));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        Ability ability = new SimpleStaticAbility(new AngelOfJubilationEffect());
        ability.addEffect(new AngelOfJubilationSacrificeFilterEffect());
        this.addAbility(ability);
    }

    private AngelOfJubilation(final AngelOfJubilation card) {
        super(card);
    }

    @Override
    public AngelOfJubilation copy() {
        return new AngelOfJubilation(this);
    }
}

class AngelOfJubilationEffect extends ContinuousEffectImpl {

    public AngelOfJubilationEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Players can't pay life or sacrifice creatures to cast spells";
    }

    public AngelOfJubilationEffect(final AngelOfJubilationEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfJubilationEffect copy() {
        return new AngelOfJubilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            player.setPayLifeCostLevel(Player.PayLifeCostLevel.nonSpellnonActivatedAbilities);
            player.setCanPaySacrificeCostFilter(new FilterCreaturePermanent());
        }
        return true;
    }
}

class AngelOfJubilationSacrificeFilterEffect extends CostModificationEffectImpl {

    public AngelOfJubilationSacrificeFilterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.SET_COST);
        staticText = "or activate abilities";
    }

    protected AngelOfJubilationSacrificeFilterEffect(AngelOfJubilationSacrificeFilterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        for (Cost cost : abilityToModify.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                Filter filter = sacrificeCost.getTargets().get(0).getFilter();
                filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {

        return (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || abilityToModify instanceof SpellAbility)
                && game.getState().getPlayersInRange(source.getControllerId(), game).contains(abilityToModify.getControllerId());
    }

    @Override
    public AngelOfJubilationSacrificeFilterEffect copy() {
        return new AngelOfJubilationSacrificeFilterEffect(this);
    }

}
