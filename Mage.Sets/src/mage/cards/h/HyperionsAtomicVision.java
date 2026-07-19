package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.BeholdAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class HyperionsAtomicVision extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public HyperionsAtomicVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // As an additional cost to cast this spell, you may behold a Hero.
        this.addAbility(new BeholdAbility(SubType.HERO));

        // Destroy target tapped creature. If a Hero was beheld, scry 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new ScryEffect(2), BeheldHeroCondition.instance,
            "if a Hero was beheld, scry 2"
        ));
    }

    private HyperionsAtomicVision(final HyperionsAtomicVision card) {
        super(card);
    }

    @Override
    public HyperionsAtomicVision copy() {
        return new HyperionsAtomicVision(this);
    }
}

enum BeheldHeroCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, BeholdAbility.BEHOLD_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "Hero was beheld";
    }
}
