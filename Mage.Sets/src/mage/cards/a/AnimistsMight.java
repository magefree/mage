package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnimistsMight extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a legendary creature you control");
    private static final FilterPermanent filter2
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public AnimistsMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // This spell costs {2} less to cast if it targets a legendary creature you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature you control deals damage equal to twice its power to target creature or planeswalker you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("", 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
    }

    private AnimistsMight(final AnimistsMight card) {
        super(card);
    }

    @Override
    public AnimistsMight copy() {
        return new AnimistsMight(this);
    }
}
