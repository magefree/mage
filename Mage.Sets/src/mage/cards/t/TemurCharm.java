package mage.cards.t;

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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TemurCharm extends CardImpl {

    private static final FilterCreaturePermanent filterCantBlock = new FilterCreaturePermanent("Creatures with power 3 or less");

    static {
        filterCantBlock.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TemurCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}{R}");

        // Choose one -
        // Target creature you control gets +1/+1 until end of turn. That creature fights target creature you don't control.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("It fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        this.getSpellAbility().addTarget(target);

        // Counter target spell unless its controller pays {3}.
        Mode mode = new Mode(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        mode.addTarget(new TargetSpell());
        this.getSpellAbility().addMode(mode);

        // Creatures with power 3 or less can't block this turn.
        mode = new Mode(new CantBlockAllEffect(filterCantBlock, Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    private TemurCharm(final TemurCharm card) {
        super(card);
    }

    @Override
    public TemurCharm copy() {
        return new TemurCharm(this);
    }
}
