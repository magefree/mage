package mage.cards.h;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class HuntersMark extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker you don't control");
    private static final FilterPermanent conditionFilter = new FilterPermanent("a blue permanent you don't control");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        conditionFilter.add(new ColorPredicate(ObjectColor.BLUE));
        conditionFilter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(conditionFilter);

    public HuntersMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // This spell costs {3} less to cast if it targets a blue permanent you don't control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature you control gets +1/+1 until end of turn. Then it deals damage equal to its power to target creature or planeswalker you don't control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it").concatBy("Then"));
    }

    private HuntersMark(final HuntersMark card) {
        super(card);
    }

    @Override
    public HuntersMark copy() {
        return new HuntersMark(this);
    }
}
