package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineGiant extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(BrineGiantCostReductionEffect.filter);

    public BrineGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // This spell costs {1} less to cast for each enchantment you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new BrineGiantCostReductionEffect()
        ).addHint(new ValueHint("Enchantments you control", xValue)));
    }

    private BrineGiant(final BrineGiant card) {
        super(card);
    }

    @Override
    public BrineGiant copy() {
        return new BrineGiant(this);
    }
}

class BrineGiantCostReductionEffect extends CostModificationEffectImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    BrineGiantCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each artifact you control";
    }

    private BrineGiantCostReductionEffect(final BrineGiantCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int count = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game).size();
        if (count > 0) {
            CardUtil.reduceCost(abilityToModify, count);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public BrineGiantCostReductionEffect copy() {
        return new BrineGiantCostReductionEffect(this);
    }
}