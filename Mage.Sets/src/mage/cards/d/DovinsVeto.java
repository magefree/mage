package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinsVeto extends CardImpl {

    public DovinsVeto(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");

        // This spell can't be countered.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new CantBeCounteredSourceEffect()
        ).setRuleAtTheTop(true));

        // Counter target noncreature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private DovinsVeto(final DovinsVeto card) {
        super(card);
    }

    @Override
    public DovinsVeto copy() {
        return new DovinsVeto(this);
    }
}
