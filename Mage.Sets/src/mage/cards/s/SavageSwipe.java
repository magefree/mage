package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavageSwipe extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SavageSwipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target creature you control gets +2/+2 until end of turn if its power is 2. Then it fights target creature you don't control.
        this.getSpellAbility().addEffect(new SavageSwipeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SavageSwipe(final SavageSwipe card) {
        super(card);
    }

    @Override
    public SavageSwipe copy() {
        return new SavageSwipe(this);
    }
}

class SavageSwipeEffect extends OneShotEffect {

    SavageSwipeEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control gets +2/+2 until end of turn if its power is 2. " +
                "Then it fights target creature you don't control.";
    }

    private SavageSwipeEffect(final SavageSwipeEffect effect) {
        super(effect);
    }

    @Override
    public SavageSwipeEffect copy() {
        return new SavageSwipeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.getPower().getValue() == 2) {
            ContinuousEffect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            game.applyEffects();
        }
        return new FightTargetsEffect().apply(game, source);
    }
}