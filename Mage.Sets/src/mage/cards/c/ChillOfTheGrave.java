package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ChillOfTheGrave extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "you control a Zombie");

    public ChillOfTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // This spell costs {1} less to cast if you control a Zombie.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ConditionHint(condition));
        this.addAbility(ability);

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ChillOfTheGrave(final ChillOfTheGrave card) {
        super(card);
    }

    @Override
    public ChillOfTheGrave copy() {
        return new ChillOfTheGrave(this);
    }
}
