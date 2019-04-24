
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author noxx
 */
public final class AngelOfJubilation extends CardImpl {

    private static final FilterCreaturePermanent filterNonBlack = new FilterCreaturePermanent("nonblack creatures");

    static {
        filterNonBlack.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public AngelOfJubilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other nonblack creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterNonBlack, true)));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelOfJubilationEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngelOfJubilationSacrificeFilterEffect()));
    }

    public AngelOfJubilation(final AngelOfJubilation card) {
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
        staticText = "Players can't pay life or sacrifice creatures to cast spells or activate abilities";
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
        for (Player player : game.getPlayers().values()) {
            player.setCanPayLifeCost(false);
            player.setCanPaySacrificeCostFilter(new FilterCreaturePermanent());
        }
        return true;
    }
}

class AngelOfJubilationSacrificeFilterEffect extends CostModificationEffectImpl {

    public AngelOfJubilationSacrificeFilterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.SET_COST);
        staticText = "Players can't pay life or sacrifice creatures to cast spells or activate abilities";
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
                filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || abilityToModify instanceof SpellAbility;
    }

    @Override
    public AngelOfJubilationSacrificeFilterEffect copy() {
        return new AngelOfJubilationSacrificeFilterEffect(this);
    }

}
