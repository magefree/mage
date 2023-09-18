package mage.cards.w;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Whack extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public Whack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // This spell costs {3} less to cast if it targets a white creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature gets -4/-4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, -4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Whack(final Whack card) {
        super(card);
    }

    @Override
    public Whack copy() {
        return new Whack(this);
    }
}
