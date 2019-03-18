package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
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
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.instance));
        this.getSpellAbility().setTargetAdjuster(DregsOfSorrowAdjuster.instance);
    }

    public DregsOfSorrow(final DregsOfSorrow card) {
        super(card);
    }

    @Override
    public DregsOfSorrow copy() {
        return new DregsOfSorrow(this);
    }
}

enum DregsOfSorrowAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(xValue, xValue, filter, false));
    }
}