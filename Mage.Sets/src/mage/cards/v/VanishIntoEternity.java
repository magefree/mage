package mage.cards.v;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.cost.SpellCostIncreaseSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;

/**
 * @author TheElk801
 */
public final class VanishIntoEternity extends CardImpl {

    private static final Condition condition
            = new SourceTargetsPermanentCondition(StaticFilters.FILTER_PERMANENT_A_CREATURE);

    public VanishIntoEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // This spell costs {3} more to cast if it targets a creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostIncreaseSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Exile target nonland permanent.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private VanishIntoEternity(final VanishIntoEternity card) {
        super(card);
    }

    @Override
    public VanishIntoEternity copy() {
        return new VanishIntoEternity(this);
    }
}
