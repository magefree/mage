package mage.cards.d;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DregsOfSorrow extends CardImpl {

    public DregsOfSorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{4}{B}");

        // Destroy X target nonblack creatures. Draw X cards.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target nonblack creatures"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private DregsOfSorrow(final DregsOfSorrow card) {
        super(card);
    }

    @Override
    public DregsOfSorrow copy() {
        return new DregsOfSorrow(this);
    }
}
