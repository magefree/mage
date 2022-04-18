package mage.cards.k;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnockoutBlow extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("a red permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public KnockoutBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // This spell costs {2} less to cast if it target a red creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Knockout Blow deals 4 damage to target attacking or blocking creature and you gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private KnockoutBlow(final KnockoutBlow card) {
        super(card);
    }

    @Override
    public KnockoutBlow copy() {
        return new KnockoutBlow(this);
    }
}
