package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Dominate extends CardImpl {

    public Dominate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{U}{U}");

        // Gain control of target creature with converted mana cost X or less.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature with mana value X or less")));
        this.getSpellAbility().setTargetAdjuster(DominateAdjuster.instance);
    }

    private Dominate(final Dominate card) {
        super(card);
    }

    @Override
    public Dominate copy() {
        return new Dominate(this);
    }
}

enum DominateAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value X or less");
        filter.add(Predicates.not(new ManaValuePredicate(ComparisonType.MORE_THAN, xValue)));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}