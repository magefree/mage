package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Addictiveme
 */
public final class SilverFurMaster extends CardImpl {

		private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ninja and Rogue creatures");

		static {
			filter.add(Predicates.or(
			        SubType.NINJA.getPredicate(),
			        SubType.ROGUE.getPredicate()
			));
		}

		public SilverFurMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ninjutsu {U}{B}
        this.addAbility(new NinjutsuAbility("{U}{B}"));

        // Ninjutsu abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilverFurMasterCostModificationEffect()));

        // Other Ninja and Rogue creatures you control get +1/+1
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

    }

    private SilverFurMaster(final SilverFurMaster card) {
        super(card);
    }

    @Override
    public SilverFurMaster copy() {
        return new SilverFurMaster(this);
    }
}

class SilverFurMasterCostModificationEffect extends CostModificationEffectImpl {

    SilverFurMasterCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Ninjutsu abilities you activate cost {1} less to activate";
    }

    SilverFurMasterCostModificationEffect(SilverFurMasterCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && (abilityToModify instanceof NinjutsuAbility);
    }

    @Override
    public SilverFurMasterCostModificationEffect copy() {
        return new SilverFurMasterCostModificationEffect(this);
    }

}