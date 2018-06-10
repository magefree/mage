
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TemurCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");
    private static final FilterCreaturePermanent filterCantBlock = new FilterCreaturePermanent("Creatures with power 3 or less");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filterCantBlock.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TemurCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}{R}");

        // Choose one -
        // <strong>�</strong> Target creature you control gets +1/+1 until end of turn. That creature fights target creature you don't control.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("That creature fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);

        // <strong>�</strong> Counter target spell unless its controller pays {3}.
        Mode mode = new Mode();
        mode.getEffects().add(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        mode.getTargets().add(new TargetSpell());
        this.getSpellAbility().addMode(mode);

        // <strong>�</strong> Creatures with power 3 or less can't block this turn.
        mode = new Mode();
        mode.getEffects().add(new CantBlockAllEffect(filterCantBlock, Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);

    }

    public TemurCharm(final TemurCharm card) {
        super(card);
    }

    @Override
    public TemurCharm copy() {
        return new TemurCharm(this);
    }
}
