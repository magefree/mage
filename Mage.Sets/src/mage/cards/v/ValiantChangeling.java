package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValiantChangeling extends CardImpl {

    public ValiantChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each creature type among creatures you control. This effect can't reduce the amount of mana this spell costs by more than {5}.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ValiantChangelingCostReductionEffect()));

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private ValiantChangeling(final ValiantChangeling card) {
        super(card);
    }

    @Override
    public ValiantChangeling copy() {
        return new ValiantChangeling(this);
    }
}

class ValiantChangelingCostReductionEffect extends CostModificationEffectImpl {

    ValiantChangelingCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each creature type among creatures you control. " +
                "This effect can't reduce the amount of mana this spell costs by more than {5}.";
    }

    private ValiantChangelingCostReductionEffect(ValiantChangelingCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Set<SubType> subTypes = new HashSet();
        int reductionAmount = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (permanent.isAllCreatureTypes(game)) {
                reductionAmount = 5;
                break;
            }
            subTypes.addAll(permanent.getSubtype(game));
            subTypes.removeIf(subType -> (subType.getSubTypeSet() != SubTypeSet.CreatureType));
            reductionAmount = subTypes.size();
            if (reductionAmount > 4) {
                break;
            }
        }
        CardUtil.reduceCost(abilityToModify, Math.min(reductionAmount, 5));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public ValiantChangelingCostReductionEffect copy() {
        return new ValiantChangelingCostReductionEffect(this);
    }
}
