package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

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
        this.getSpellAbility().setTargetAdjuster(DregsOfSorrowAdjuster.instance);
    }

    private DregsOfSorrow(final DregsOfSorrow card) {
        super(card);
    }

    @Override
    public DregsOfSorrow copy() {
        return new DregsOfSorrow(this);
    }
}

enum DregsOfSorrowAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(xValue, xValue, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, false));
    }
}
