
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class GhaltaPrimalHunger extends CardImpl {

    public GhaltaPrimalHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Ghalta, Primal Hunger costs {X} less to cast, where X is the total power of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GhaltaPrimalHungerCostReductionEffect()));
        // Trample
        this.addAbility(TrampleAbility.getInstance());

    }

    private GhaltaPrimalHunger(final GhaltaPrimalHunger card) {
        super(card);
    }

    @Override
    public GhaltaPrimalHunger copy() {
        return new GhaltaPrimalHunger(this);
    }
}

class GhaltaPrimalHungerCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("noncreature artifacts you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    GhaltaPrimalHungerCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {X} less to cast, where X is the total power of creatures you control";
    }

    GhaltaPrimalHungerCostReductionEffect(final GhaltaPrimalHungerCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int totalPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isCreature(game)) {
                totalPower += permanent.getPower().getValue();
            }

        }
        CardUtil.reduceCost(abilityToModify, totalPower);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility);
    }

    @Override
    public GhaltaPrimalHungerCostReductionEffect copy() {
        return new GhaltaPrimalHungerCostReductionEffect(this);
    }
}
