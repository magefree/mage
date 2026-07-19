package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 * @author muz
 */
public final class ThingSwing extends CardImpl {

    private static final FilterPermanent filter
        = new FilterControlledPermanent(SubType.HERO, "a Hero you control");

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public ThingSwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // This spell costs {2} less to cast if it targets a Hero you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature you control deals damage equal to twice its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("", 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private ThingSwing(final ThingSwing card) {
        super(card);
    }

    @Override
    public ThingSwing copy() {
        return new ThingSwing(this);
    }
}
